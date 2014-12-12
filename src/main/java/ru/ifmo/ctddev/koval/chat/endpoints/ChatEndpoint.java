package ru.ifmo.ctddev.koval.chat.endpoints;

import ru.ifmo.ctddev.koval.chat.cdi.ChatHistoryManager;
import ru.ifmo.ctddev.koval.chat.decoders.ChatMessageDecoder;
import ru.ifmo.ctddev.koval.chat.encoders.ChatMessageEncoder;
import ru.ifmo.ctddev.koval.chat.model.visitor.chat.ChatMessageVisitor;
import ru.ifmo.ctddev.koval.chat.model.pojo.chat.*;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(
        value = "/chat",
        decoders = {ChatMessageDecoder.class},
        encoders = {ChatMessageEncoder.class},
        configurator = DefaultEndpointConfigurator.class
)
@Stateless
public class ChatEndpoint extends AbstractEndpoint {

    @Inject
    ChatHistoryManager chatHistoryManager;

    protected static final @Nonnull ConcurrentHashMap<String, Session> userIdToSessionsCache = new ConcurrentHashMap<>();
    protected static final @Nonnull ConcurrentHashMap<Session, String> sessionToTokenCache = new ConcurrentHashMap<>();

    @Nonnull
    @Override
    protected ConcurrentHashMap<String, Session> getUserIdToSessionsCache() {
        return userIdToSessionsCache;
    }

    @Nonnull
    @Override
    protected ConcurrentHashMap<Session, String> getSessionToTokenCache() {
        return sessionToTokenCache;
    }

    @OnMessage
    public void onMessage(final @Nonnull Session client, @Nonnull ChatMessage message) {
        message.visitBy(new ChatMessageVisitor() {
            @Override
            public void visitConversationItem(@Nonnull ConversationItemMessage item) {
                Session sender = userIdToSessionsCache.get(item.getIdFrom());
                if (sender != client) {
                    throw new IllegalStateException("Unexpected sender");
                }

                item = chatHistoryManager.update(item);
                Session counterpart = userIdToSessionsCache.get(item.getIdTo());
                if (counterpart != null) {
                    counterpart.getAsyncRemote().sendObject(item);
                }
                client.getAsyncRemote().sendObject(new ConversationItemConfirmationMessage(item));
            }

            @Override
            public void visitHistoryRequest(@Nonnull HistoryRequestMessage item) {
                String token = sessionToTokenCache.get(client);
                String userId = sessionManager.getUserIdByToken(token);
                if (userId == null) {
                    return;
                }
                Set<ConversationItemMessage> history = chatHistoryManager.getHistory(userId, item);
                client.getAsyncRemote().sendObject(new HistoryResponseMessage(history));
            }

            @Override
            public void visitConversationRequest(@Nonnull ConversationRequestMessage item) {
                String token = sessionToTokenCache.get(client);
                String userId = sessionManager.getUserIdByToken(token);
                if (userId == null) {
                    return;
                }
                Set<Conversation> conversations = chatHistoryManager.getConversations(userId);
                client.getAsyncRemote().sendObject(new ConversationResponseMessage(conversations));
            }

            @Override
            public void visitConversationResponse(@Nonnull ConversationResponseMessage item) {
                throw new IllegalStateException("Shouldn't happen");
            }

            @Override
            public void visitHistoryResponse(@Nonnull HistoryResponseMessage item) {
                throw new IllegalStateException("Shouldn't happen");
            }

            @Override
            public void visitDefault(@Nonnull ChatMessage item) {
                throw new IllegalStateException("Shouldn't happen");
            }
        });
    }

}
