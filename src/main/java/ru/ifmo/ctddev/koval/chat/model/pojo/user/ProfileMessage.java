package ru.ifmo.ctddev.koval.chat.model.pojo.user;

import ru.ifmo.ctddev.koval.chat.model.pojo.AbstractMessage;
import ru.ifmo.ctddev.koval.chat.model.util.Visitable;
import ru.ifmo.ctddev.koval.chat.model.visitor.profile.ProfileMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.JsonObject;

public abstract class ProfileMessage extends AbstractMessage implements Visitable<ProfileMessage, ProfileMessageVisitor> {

    public ProfileMessage(@Nonnull JsonObject object) {
    }

    protected ProfileMessage() {
    }

    @Override
    public void visitBy(@Nonnull ProfileMessageVisitor visitor) {
        visitor.visitDefault(this);
    }
}