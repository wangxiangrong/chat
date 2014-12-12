package ru.ifmo.ctddev.koval.chat.cdi;

import ru.ifmo.ctddev.koval.chat.model.pojo.chat.Conversation;
import ru.ifmo.ctddev.koval.chat.model.pojo.chat.ConversationItemMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.chat.ConversationResponseMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.chat.HistoryRequestMessage;

import javax.annotation.Nonnull;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

@Named
public class ChatHistoryManager {

    private final @Nonnull ConcurrentHashMap<UserPair, ConcurrentSkipListSet<ConversationItemMessage>> conversations = new ConcurrentHashMap<>();
    private final @Nonnull AtomicLong id = new AtomicLong(0);

    /* * * * * * * * * * * * * *
            PUBLIC API
     * * * * * * * * * * * * * * */
    public ConversationItemMessage update(@Nonnull ConversationItemMessage message) {
        message = new ConversationItemMessage(message.getMessageUuid(), id.incrementAndGet(), message.getIdFrom(), message.getIdTo(), message.getMessage(), System.currentTimeMillis());

        final UserPair pair = new UserPair(message.getIdFrom(), message.getIdTo());
        if (!conversations.containsKey(pair)) {
            conversations.putIfAbsent(pair, newSet());
        }
        conversations.get(pair).add(message);

        return message;
    }

    @Nonnull
    public Set<ConversationItemMessage> getHistory(@Nonnull String userId, @Nonnull HistoryRequestMessage message) {
        final UserPair pair = new UserPair(userId, message.getUserId());
        ConcurrentSkipListSet<ConversationItemMessage> history = conversations.get(pair);
        if (history == null) {
            return Collections.emptySet();
        }

        final ConversationItemMessage fake = new ConversationItemMessage("", message.getIdFrom(), "", "", "", 0);
        switch (message.getDirection()) {
            case BEFORE:
                return new TreeSet<>(history.tailSet(fake, true));
            case AFTER:
                return new TreeSet<>(history.headSet(fake, true));
        }
        return Collections.emptySet();
    }

    @Nonnull
    public Set<Conversation> getConversations(@Nonnull String userId) {
        final Set<Conversation> result = new TreeSet<>(new Conversation.ProxyComparator(ConversationItemMessage.descendingMessageId()));

        for (Map.Entry<UserPair, ConcurrentSkipListSet<ConversationItemMessage>> entry : conversations.entrySet()) {
            if (entry.getKey().id1.equals(userId) || entry.getKey().id2.equals(userId)) {
                ConcurrentSkipListSet<ConversationItemMessage> messages = entry.getValue();
                ConversationResponseMessage.ConversationType type = null;
                for (ConversationItemMessage message : messages) {
                    if (message.getIdFrom().equals(userId)) {
                        if (type == ConversationResponseMessage.ConversationType.INCOMING) {
                            type = ConversationResponseMessage.ConversationType.BOTH;
                            break;
                        }
                        type = ConversationResponseMessage.ConversationType.OUTGOING;
                    } else {
                        if (type == ConversationResponseMessage.ConversationType.OUTGOING) {
                            type = ConversationResponseMessage.ConversationType.BOTH;
                            break;
                        }
                        type = ConversationResponseMessage.ConversationType.INCOMING;
                    }
                }

                if (type != null) {
                    result.add(new Conversation(messages.first(), type));
                }
            }
        }

        return result;
    }

    /* * * * * * * * * * * * * *
            PRIVATE API
     * * * * * * * * * * * * * * */
    @Nonnull
    private ConcurrentSkipListSet<ConversationItemMessage> newSet() {
        return new ConcurrentSkipListSet<>(ConversationItemMessage.descendingMessageId());
    }

    /**
     * Represents a user pair. Ids are swappable.
     */
    private static class UserPair {

        private final @Nonnull String id1;
        private final @Nonnull String id2;

        private UserPair(@Nonnull String id1, @Nonnull String id2) {
            this.id1 = id1;
            this.id2 = id2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserPair userPair = (UserPair) o;

            return id1.equals(userPair.id1) && id2.equals(userPair.id2) ||
                    id2.equals(userPair.id1) && id1.equals(userPair.id2);

        }

        @Override
        public int hashCode() {
            return id1.hashCode() + id2.hashCode();
        }
    }
}
