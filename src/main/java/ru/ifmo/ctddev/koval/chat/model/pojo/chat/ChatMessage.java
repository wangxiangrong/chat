package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import ru.ifmo.ctddev.koval.chat.model.pojo.AbstractMessage;
import ru.ifmo.ctddev.koval.chat.model.util.Visitable;
import ru.ifmo.ctddev.koval.chat.model.visitor.chat.ChatMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.JsonObject;

public abstract class ChatMessage extends AbstractMessage implements Visitable<ChatMessage, ChatMessageVisitor> {

    public ChatMessage(@Nonnull JsonObject object) {
    }

    protected ChatMessage() {
    }

    @Override
    public void visitBy(@Nonnull ChatMessageVisitor visitor) {
        visitor.visitDefault(this);
    }
}
