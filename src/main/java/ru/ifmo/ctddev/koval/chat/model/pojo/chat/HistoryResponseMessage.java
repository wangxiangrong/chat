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

public class HistoryResponseMessage extends ChatMessage {
    public static final @Nonnull String ID = "history_resp";

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    private static final @Nonnull String MESSAGES = "messages";

    private final @Nonnull Collection<ConversationItemMessage> messages;

    public HistoryResponseMessage(@Nonnull JsonObject object) {
        super(object);
        final TreeSet<ConversationItemMessage> treeSet = new TreeSet<>(ConversationItemMessage.descendingMessageId());
        JsonArray array = object.getJsonArray(MESSAGES);
        for (int i = 0; i < array.size(); i++) {
            treeSet.add(new ConversationItemMessage(array.getJsonObject(i)));
        }
        messages = Collections.unmodifiableSet(treeSet);
    }

    public HistoryResponseMessage(@Nonnull Collection<ConversationItemMessage> messages) {
        this.messages = messages;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (ConversationItemMessage message : messages) {
            arrayBuilder.add(message.encodeBody());
        }
        return Json.createObjectBuilder().add(MESSAGES, arrayBuilder.build()).build();
    }

    @Override
    public void visitBy(@Nonnull ChatMessageVisitor visitor) {
        visitor.visitHistoryResponse(this);
    }
}
