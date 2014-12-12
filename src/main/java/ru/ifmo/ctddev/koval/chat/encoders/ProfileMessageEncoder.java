package ru.ifmo.ctddev.koval.chat.encoders;

import ru.ifmo.ctddev.koval.chat.model.pojo.AbstractMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileMessage;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public final class ProfileMessageEncoder implements Encoder.Text<ProfileMessage> {
    @Override
    public String encode(@Nonnull ProfileMessage profileMessage) throws EncodeException {
        return Json.createObjectBuilder().
                add(AbstractMessage.MESSAGE_TYPE_ID, profileMessage.getMessageTypeId()).
                add(AbstractMessage.MESSAGE_BODY, profileMessage.encodeBody()).
                build().toString();
    }

    @Override
    public void init(@Nonnull EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
