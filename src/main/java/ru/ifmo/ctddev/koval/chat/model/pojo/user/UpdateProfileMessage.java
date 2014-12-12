package ru.ifmo.ctddev.koval.chat.model.pojo.user;

import ru.ifmo.ctddev.koval.chat.model.visitor.profile.ProfileMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.JsonObject;

public final class UpdateProfileMessage extends ProfileMessage {
    public static final @Nonnull String ID = "update";

    private final @Nonnull User user;

    public UpdateProfileMessage(@Nonnull JsonObject messageBody) {
        user = new User(messageBody);
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        return user.encodeBody();
    }

    @Override
    public void visitBy(@Nonnull ProfileMessageVisitor visitor) {
        visitor.visitUpdateProfileResponse(this);
    }
}
