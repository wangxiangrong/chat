package ru.ifmo.ctddev.koval.chat.model.visitor.profile;

import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileListRequestMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileListResponseMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.ProfileMessage;
import ru.ifmo.ctddev.koval.chat.model.pojo.user.UpdateProfileMessage;
import ru.ifmo.ctddev.koval.chat.model.util.Visitor;

import javax.annotation.Nonnull;

public interface ProfileMessageVisitor extends Visitor<ProfileMessage, ProfileMessageVisitor> {
    public void visitProfileListResponse(@Nonnull ProfileListResponseMessage item);

    public void visitProfileListRequest(@Nonnull ProfileListRequestMessage item);

    public void visitUpdateProfileResponse(@Nonnull UpdateProfileMessage item);

}
