package ru.ifmo.ctddev.koval.chat.encoders;

import ru.ifmo.ctddev.koval.chat.model.pojo.AbstractMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.chat.ChatMessage;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    @Override
    public String encode(@Nonnull ChatMessage chatMessage) throws EncodeException {
        return Json.createObjectBuilder().add(AbstractMessage.MESSAGE_TYPE_ID, chatMessage.getMessageTypeId()).
                add(AbstractMessage.MESSAGE_BODY, chatMessage.encodeBody()).build().toString();
    }

    @Override
    public void init(@Nonnull EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
