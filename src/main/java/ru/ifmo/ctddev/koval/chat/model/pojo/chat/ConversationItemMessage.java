package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import ru.ifmo.ctddev.koval.chat.model.visitor.chat.ChatMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.Comparator;

public class ConversationItemMessage extends ChatMessage {
    public static final String ID = "message";

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    private static final @Nonnull String MESSAGE_UUID = "uuid";
    private static final @Nonnull String MESSAGE_ID = "id";
    private static final @Nonnull String FROM_ID = "fromUserId";
    private static final @Nonnull String TO_ID = "toUserId";
    private static final @Nonnull String MESSAGE = "message";
    private static final @Nonnull String DATE = "datetime";

    private final @Nonnull String messageUuid;
    private final long messageId;
    private final @Nonnull String idFrom;
    private final @Nonnull String idTo;
    private final @Nonnull String message;
    private final long date;

    public ConversationItemMessage(@Nonnull JsonObject jsonObject) {
        super(jsonObject);
        messageUuid = jsonObject.getJsonString(MESSAGE_UUID).getString();
        messageId = jsonObject.getJsonNumber(MESSAGE_ID).longValueExact();
        idFrom = jsonObject.getJsonString(FROM_ID).getString();
        idTo = jsonObject.getJsonString(TO_ID).getString();
        message = jsonObject.getJsonString(MESSAGE).getString();
        date = jsonObject.getJsonNumber(DATE).longValueExact();
    }

    public ConversationItemMessage(@Nonnull String messageUuid, long messageId, @Nonnull String idFrom,
                                   @Nonnull String idTo, @Nonnull String message, long date) {
        this.messageUuid = messageUuid;
        this.messageId = messageId;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.message = message;
        this.date = date;
    }

    @Nonnull
    public String getMessageUuid() {
        return messageUuid;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getDate() {
        return date;
    }

    @Nonnull
    public String getIdFrom() {
        return idFrom;
    }

    @Nonnull
    public String getIdTo() {
        return idTo;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        return Json.createObjectBuilder().add(MESSAGE_UUID, messageUuid).add(DATE, date).
                add(FROM_ID, idFrom).add(TO_ID, idTo).add(MESSAGE, message).build();
    }

    @Nonnull
    public static Comparator<ConversationItemMessage> descendingMessageId() {
        return new Comparator<ConversationItemMessage>() {
            @Override
            public int compare(ConversationItemMessage o1, ConversationItemMessage o2) {
                return Long.compare(o2.messageId, o1.messageId);
            }
        };
    }

    @Override
    public void visitBy(@Nonnull ChatMessageVisitor visitor) {
        visitor.visitConversationItem(this);
    }
}