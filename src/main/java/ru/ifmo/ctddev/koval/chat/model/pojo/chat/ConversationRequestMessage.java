package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import ru.ifmo.ctddev.koval.chat.model.visitor.chat.ChatMessageVisitor;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.json.JsonObject;

@Immutable
public final class ConversationRequestMessage extends ChatMessage {
    public static final @Nonnull String ID = "conv_list_req";

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    public ConversationRequestMessage(@Nonnull JsonObject messageBody) {
        super(messageBody);
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        throw new UnsupportedOperationException("This message can't be sent back to client");
    }

    @Override
    public void visitBy(@Nonnull ChatMessageVisitor visitor) {
        visitor.visitConversationRequest(this);
    }
}
