package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.json.JsonObject;

@Immutable
public final class ConversationItemConfirmationMessage extends ChatMessage {
    public static final @Nonnull String ID = "message_confirm";

    private final @Nonnull ConversationItemMessage message;

    public ConversationItemConfirmationMessage(@Nonnull ConversationItemMessage message) {
        this.message = message;
    }

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        return message.encodeBody();
    }
}
