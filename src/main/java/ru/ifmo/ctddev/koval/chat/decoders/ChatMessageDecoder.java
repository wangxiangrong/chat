package ru.ifmo.ctddev.koval.chat.decoders;

import ru.ifmo.ctddev.koval.chat.model.pojo.AbstractMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.chat.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

    private @Nullable ChatMessage decodedMessage;

    @Override
    @Nullable
    public ChatMessage decode(@Nonnull String s) throws DecodeException {
        if (willDecode(s)) {
            return decodedMessage;
        }
        throw new DecodeException(s, "Couldn't decode message = " + s);
    }

    @Override
    public boolean willDecode(@Nonnull String s) {
        JsonReader reader = Json.createReader(new StringReader(s));

        JsonObject jsonObject = reader.readObject();
        JsonString messageTypeId = jsonObject.getJsonString(AbstractMessage.MESSAGE_TYPE_ID);
        JsonObject messageBody = jsonObject.getJsonObject(AbstractMessage.MESSAGE_BODY);
        if (messageTypeId == null || messageBody == null) {
            return false;
        }

        switch (messageTypeId.getString()) {
            case ConversationItemMessage.ID:
                decodedMessage = new ConversationItemMessage(messageBody);
                break;
            case HistoryRequestMessage.ID:
                decodedMessage = new HistoryRequestMessage(messageBody);
                break;
            case HistoryResponseMessage.ID:
                decodedMessage = new HistoryResponseMessage(messageBody);
                break;
            case ConversationRequestMessage.ID:
                decodedMessage = new ConversationRequestMessage(messageBody);
                break;
            case ConversationResponseMessage.ID:
                decodedMessage = new ConversationResponseMessage(messageBody);
                break;
            default:
                decodedMessage = null;
                break;
        }


        return decodedMessage != null;
    }

    @Override
    public void init(@Nonnull EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}