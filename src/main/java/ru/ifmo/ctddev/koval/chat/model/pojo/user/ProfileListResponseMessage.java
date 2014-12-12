package ru.ifmo.ctddev.koval.chat.model.pojo.user;

import ru.ifmo.ctddev.koval.chat.model.visitor.profile.ProfileMessageVisitor;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Collection;

public class ProfileListResponseMessage extends ProfileMessage {
    public static final @Nonnull String ID = "profile_list_resp";

    private static final @Nonnull String REQUEST_ID = "req_id";
    private static final @Nonnull String PROFILES = "profiles";

    private final @Nonnull String requestId;
    private final @Nonnull Collection<User> profiles;

    public ProfileListResponseMessage(@Nonnull JsonObject jsonObject) {
        super(jsonObject);
        requestId = jsonObject.getString(REQUEST_ID);

        final JsonArray jsonArray = jsonObject.getJsonArray(PROFILES);
        profiles = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            profiles.add(new User(jsonArray.getJsonObject(i)));
        }
    }

    public ProfileListResponseMessage(@Nonnull String requestId, @Nonnull Collection<User> profiles) {
        this.requestId = requestId;
        this.profiles = profiles;
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
        for (User user : profiles) {
            arrayBuilder.add(user.encodeBody());
        }
        return Json.createObjectBuilder().
                add(REQUEST_ID, requestId).
                add(PROFILES, arrayBuilder.build()).
                build();
    }

    @Override
    public void visitBy(@Nonnull ProfileMessageVisitor visitor) {
        visitor.visitProfileListResponse(this);
    }
}
