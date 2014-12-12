package ru.ifmo.ctddev.koval.chat.model.visitor.chat;

import ru.ifmo.ctddev.koval.chat.model.pojo.chat.*;

import javax.annotation.Nonnull;

public class DefaultChatMessageVisitor implements ChatMessageVisitor {
    @Override
    public void visitConversationItem(@Nonnull ConversationItemMessage item) {
        visitDefault(item);
    }

    @Override
    public void visitHistoryRequest(@Nonnull HistoryRequestMessage item) {
        visitDefault(item);
    }

    @Override
    public void visitHistoryResponse(@Nonnull HistoryResponseMessage item) {
        visitDefault(item);
    }

    @Override
    public void visitConversationRequest(@Nonnull ConversationRequestMessage item) {
        visitDefault(item);
    }

    @Override
    public void visitConversationResponse(@Nonnull ConversationResponseMessage item) {
        visitDefault(item);
    }

    @Override
    public void visitDefault(@Nonnull ChatMessage item) {
    }
}
