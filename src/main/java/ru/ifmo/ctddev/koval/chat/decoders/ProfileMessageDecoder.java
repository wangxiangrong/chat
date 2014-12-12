package ru.ifmo.ctddev.koval.chat.decoders;

import ru.ifmo.ctddev.koval.chat.model.pojo.AbstractMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileListRequestMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileListResponseMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.UpdateProfileMessage;

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

public final class ProfileMessageDecoder implements Decoder.Text<ProfileMessage> {
    private @Nullable ProfileMessage decodedMessage;

    @Override
    public ProfileMessage decode(@Nonnull String s) throws DecodeException {
        if (willDecode(s)) {
            return decodedMessage;
        }
        throw new DecodeException(s, "Couldn't decode message = " + s);
    }

    @Override
    public boolean willDecode(@Nonnull String s) {
        JsonReader reader = Json.createReader(new StringReader(s));

        final JsonObject jsonObject = reader.readObject();
        final JsonString messageTypeId = jsonObject.getJsonString(AbstractMessage.MESSAGE_TYPE_ID);
        final JsonObject messageBody = jsonObject.getJsonObject(AbstractMessage.MESSAGE_BODY);
        if (messageTypeId == null || messageBody == null) {
            return false;
        }

        switch (messageTypeId.getString()) {
            case ProfileListRequestMessage.ID:
                decodedMessage = new ProfileListRequestMessage(messageBody);
                break;
            case ProfileListResponseMessage.ID:
                decodedMessage = new ProfileListResponseMessage(messageBody);
                break;
            case UpdateProfileMessage.ID:
                decodedMessage = new UpdateProfileMessage(messageBody);
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
