package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Comparator;

@Immutable
public final class Conversation {
    public static class ProxyComparator implements Comparator<Conversation> {
        private final @Nonnull Comparator<ConversationItemMessage> comparator;

        public ProxyComparator(@Nonnull Comparator<ConversationItemMessage> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(Conversation o1, Conversation o2) {
            return comparator.compare(o1.getMessage(), o2.getMessage());
        }
    }

    private final @Nonnull ConversationItemMessage message;
    private final @Nonnull ConversationResponseMessage.ConversationType type;

    public Conversation(@Nonnull ConversationItemMessage message, @Nonnull ConversationResponseMessage.ConversationType type) {
        this.message = message;
        this.type = type;
    }

    @Nonnull
    public ConversationItemMessage getMessage() {
        return message;
    }

    @Nonnull
    public ConversationResponseMessage.ConversationType getType() {
        return type;
    }
}
