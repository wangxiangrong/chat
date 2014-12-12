package ru.ifmo.ctddev.koval.chat.model.pojo.user;

import ru.ifmo.ctddev.koval.chat.model.visitor.profile.ProfileMessageVisitor;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

@Immutable
public final class ProfileListRequestMessage extends ProfileMessage {
    public static final @Nonnull String ID = "profile_list_req";

    private static final @Nonnull String REQUEST_ID = "req_id";
    private static final @Nonnull String PROFILES = "profile_ids";

    private final @Nonnull String requestId;
    private final @Nonnull String[] profileIds;

    public ProfileListRequestMessage(@Nonnull JsonObject jsonObject) {
        super(jsonObject);
        requestId = jsonObject.getJsonString(REQUEST_ID).getString();

        final JsonArray jsonArray = jsonObject.getJsonArray(PROFILES);
        profileIds = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            profileIds[i] = jsonArray.getString(i);
        }
    }

    @Nonnull
    public String getRequestId() {
        return requestId;
    }

    @Nonnull
    public String[] getProfileIds() {
        return profileIds;
    }

    @Nonnull
    @Override
    public String getMessageTypeId() {
        return ID;
    }

    @Nonnull
    @Override
    public JsonObject encodeBody() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String profileId : profileIds) {
            arrayBuilder.add(profileId);
        }
        return Json.createObjectBuilder().
                add(REQUEST_ID, requestId).
                add(PROFILES, arrayBuilder.build()).
                build();
    }

    @Override
    public void visitBy(@Nonnull ProfileMessageVisitor visitor) {
        visitor.visitProfileListRequest(this);
    }
}
