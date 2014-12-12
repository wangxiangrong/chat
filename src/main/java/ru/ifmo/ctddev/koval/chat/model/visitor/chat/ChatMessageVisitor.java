package ru.ifmo.ctddev.koval.chat.model.visitor.chat;

import ru.ifmo.ctddev.koval.chat.model.pojo.chat.*;
import ru.ifmo.ctddev.koval.chat.model.util.Visitor;

import javax.annotation.Nonnull;

public interface ChatMessageVisitor extends Visitor<ChatMessage, ChatMessageVisitor> {
    void visitConversationItem(@Nonnull ConversationItemMessage item);
    void visitHistoryRequest(@Nonnull HistoryRequestMessage item);
    void visitHistoryResponse(@Nonnull HistoryResponseMessage item);
    void visitConversationRequest(@Nonnull ConversationRequestMessage item);
    void visitConversationResponse(@Nonnull ConversationResponseMessage item);
}
