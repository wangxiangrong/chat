package ru.ifmo.ctddev.koval.chat.endpoints;

import ru.ifmo.ctddev.koval.chat.cdi.UserManager;
import ru.ifmo.ctddev.koval.chat.decoders.ProfileMessageDecoder;
import ru.ifmo.ctddev.koval.chat.encoders.ProfileMessageEncoder;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.*;
import ru.ifmo.ctddev.koval.chat.model.visitor.profile.ProfileMessageVisitor;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(
        value = "/profile",
        decoders = {ProfileMessageDecoder.class},
        encoders = {ProfileMessageEncoder.class},
        configurator = DefaultEndpointConfigurator.class
)
@Stateless
public class ProfileEndpoint extends AbstractEndpoint {
    @Inject
    UserManager profileManager;

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
    public void onMessage(final Session client, ProfileMessage message) {
        message.visitBy(new ProfileMessageVisitor() {
            @Override
            public void visitProfileListResponse(@Nonnull ProfileListResponseMessage item) {
            }

            @Override
            public void visitProfileListRequest(@Nonnull ProfileListRequestMessage item) {
                final String requestId = item.getRequestId();
                final String[] profileIds = item.getProfileIds();

                final Collection<User> profiles = profileManager.getUserProfiles(profileIds);
                client.getAsyncRemote().sendObject(new ProfileListResponseMessage(requestId, profiles));
            }

            @Override
            public void visitUpdateProfileResponse(@Nonnull UpdateProfileMessage item) {
                profileManager.updateUserProfile(item.getUser());
            }

            @Override
            public void visitDefault(@Nonnull ProfileMessage item) {
            }
        });
    }
}
