package ru.ifmo.ctddev.koval.chat.model.pojo;

import javax.annotation.Nonnull;
import javax.json.JsonObject;

public abstract class AbstractMessage {
    public static final @Nonnull String MESSAGE_TYPE_ID = "messageTypeId";
    public static final @Nonnull String MESSAGE_BODY = "messageBody";

    @Nonnull
    public abstract String getMessageTypeId();

    @Nonnull
    public abstract JsonObject encodeBody();
}
