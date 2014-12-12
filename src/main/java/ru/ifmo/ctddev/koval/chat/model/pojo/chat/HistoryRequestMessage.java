package ru.ifmo.ctddev.koval.chat.model.pojo.chat;

import ru.ifmo.ctddev.koval.chat.model.visitor.chat.ChatMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.JsonObject;

public class HistoryRequestMessage extends ChatMessage {
    public enum Direction {
        AFTER("after"),
        BEFORE("before");

        private final @Nonnull String jsonValue;
        Direction(@Nonnull String jsonValue) {
            this.jsonValue = jsonValue;
        }

        @Nonnull
        public static Direction fromString(@Nonnull String string) {
            for (Direction direction : values()) {
                if (direction.jsonValue.equals(string)) {
                    return direction;
                }
            }
            throw new UnsupportedOperationException("No Direction for value = " + string);
        }
    }

    public static final @Nonnull String ID = "history_req";

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    private final @Nonnull String userId;
    private final long idFrom;
    private final long amountToFetch;
    private final @Nonnull Direction direction;

    public HistoryRequestMessage(@Nonnull JsonObject messageBody) {
        super(messageBody);
        userId = messageBody.getString("userId");
        idFrom = messageBody.getJsonNumber("idFrom").longValueExact();
        amountToFetch = messageBody.getJsonNumber("amount").longValueExact();
        direction = Direction.fromString(messageBody.getString("direction", Direction.BEFORE.jsonValue));
    }

    @Nonnull
    public String getUserId() {
        return userId;
    }

    public long getIdFrom() {
        return idFrom;
    }

    public long getAmountToFetch() {
        return amountToFetch;
    }

    @Nonnull
    public Direction getDirection() {
        return direction;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        throw new UnsupportedOperationException("This message can't be sent back to client");
    }

    @Override
    public void visitBy(@Nonnull ChatMessageVisitor visitor) {
        visitor.visitHistoryRequest(this);
    }
}
