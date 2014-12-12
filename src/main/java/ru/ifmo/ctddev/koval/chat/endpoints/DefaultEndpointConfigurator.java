package ru.ifmo.ctddev.koval.chat.endpoints;

import javax.annotation.Nonnull;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Configurator for {@link AbstractEndpoint}.
 * <p>
 * All endpoints which implement {@link AbstractEndpoint}
 * should use this configurator.
 */
public class DefaultEndpointConfigurator extends ServerEndpointConfig.Configurator {

    public static final @Nonnull String HANDSHAKE_REQUEST_KEY = "handshakeRequest";

    @Override
    public void modifyHandshake(@Nonnull ServerEndpointConfig conf,
                                @Nonnull HandshakeRequest req,
                                @Nonnull HandshakeResponse resp) {
        conf.getUserProperties().put(HANDSHAKE_REQUEST_KEY, req);
    }
}
