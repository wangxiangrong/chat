package ru.ifmo.ctddev.koval.chat.endpoints;

import ru.ifmo.ctddev.koval.chat.cdi.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public abstract class AbstractEndpoint {

    public static final @Nonnull String SESSION_ID_NAME = "sessionId";
    private static final @Nonnull Logger Log = LoggerFactory.getLogger(AbstractEndpoint.class);

    @Inject
    SessionManager sessionManager;

    @Resource(name = "comp/DefaultManagedScheduledExecutorService ")
    private ManagedScheduledExecutorService mes;

    @Nonnull
    protected abstract ConcurrentHashMap<String, Session> getUserIdToSessionsCache();

    @Nonnull
    protected abstract ConcurrentHashMap<Session, String> getSessionToTokenCache();

    @OnError
    public void onError(final @Nonnull Session client, @Nonnull Throwable error) {
        try {
            client.close(new CloseReason(CloseReason.CloseCodes.CLOSED_ABNORMALLY, error.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws java.lang.IllegalArgumentException if session id not contains in header exactly one times
     */
    @OnOpen
    public void onOpen(final @Nonnull Session session, @Nonnull EndpointConfig config) {
        mes.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    session.getBasicRemote().sendPing(ByteBuffer.allocate(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 30, TimeUnit.SECONDS);

        HandshakeRequest req = (HandshakeRequest) config.getUserProperties().get(DefaultEndpointConfigurator.HANDSHAKE_REQUEST_KEY);
        List<String> sessionIdHeader = req.getHeaders().get(SESSION_ID_NAME);

        if (sessionIdHeader == null) {
            sessionIdHeader = session.getRequestParameterMap().get(SESSION_ID_NAME);
        }

        if (sessionIdHeader == null || sessionIdHeader.size() != 1) {
            throw new IllegalArgumentException("Session id should be contains exactly one time");
        }
        setSessionId(session, sessionIdHeader.get(0));
    }

    @OnClose
    public void onClose(@Nonnull Session session, @Nonnull CloseReason closeReason) {
        String token = getSessionToTokenCache().remove(session);
        if (token != null) {
            String userId = sessionManager.getUserIdByToken(token);
            if (userId != null) {
                getUserIdToSessionsCache().remove(userId, session);
            }
        }
    }

    protected void setSessionId(@Nonnull Session client, @Nonnull String sessionId) {
        String userId = sessionManager.getUserIdByToken(sessionId);

        Session oldSession = getUserIdToSessionsCache().put(userId, client);
        if (oldSession != null && oldSession.isOpen()) {
            try {
                oldSession.close(new CloseReason(CloseReason.CloseCodes.TRY_AGAIN_LATER, "Connected from another place"));
            } catch (IOException e) {
                Log.warn("Cannot set session id for client's session #" + client.getId(), e);
            }
        }

        getSessionToTokenCache().put(client, sessionId);
    }
}
