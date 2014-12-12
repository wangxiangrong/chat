package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import ru.ifmo.ctddev.koval.chat.model.visitor.chat.ChatMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public final class ConversationResponseMessage extends ChatMessage {
    public enum ConversationType {
        INCOMING("i"), OUTGOING("o"), BOTH("b");

        private final @Nonnull String value;

        ConversationType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public static ConversationType fromJsonValue(@Nonnull String value) {
            for (ConversationType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalStateException("Not type for value = " + value);
        }
    }

    public static final @Nonnull String ID = "conv_list_resp";

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    private static final @Nonnull String CONVERSATIONS = "conversations";
    private static final @Nonnull String MESSAGE = "message";
    private static final @Nonnull String TYPE = "type";

    private final @Nonnull Collection<Conversation> conversations;

    public ConversationResponseMessage(@Nonnull JsonObject object) {
        super(object);
        final TreeSet<Conversation> treeSet = new TreeSet<>(new Conversation.ProxyComparator(ConversationItemMessage.descendingMessageId()));
        JsonArray array = object.getJsonArray(CONVERSATIONS);
        for (int i = 0; i < array.size(); i++) {
            JsonObject jsonObject = array.getJsonObject(i);
            treeSet.add(new Conversation(new ConversationItemMessage(jsonObject.getJsonObject(MESSAGE)),
                    ConversationType.fromJsonValue(jsonObject.getString(TYPE))));
        }
        conversations = Collections.unmodifiableSet(treeSet);
    }

    public ConversationResponseMessage(@Nonnull Collection<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Conversation conversation : conversations) {
            arrayBuilder.add(Json.createObjectBuilder().add(MESSAGE, conversation.getMessage().encodeBody())
                .add(TYPE, conversation.getType().value));
        }
        return Json.createObjectBuilder().add(CONVERSATIONS, arrayBuilder.build()).build();
    }

    @Override
    public void visitBy(@Nonnull ChatMessageVisitor visitor) {
        visitor.visitConversationResponse(this);
    }
}
