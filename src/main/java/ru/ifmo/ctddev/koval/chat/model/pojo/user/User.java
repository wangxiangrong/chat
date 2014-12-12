package ru.ifmo.ctddev.koval.chat.model.pojo.user;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

@Immutable
public final class User {
    private static final String ID = "id";
    private static final String NAME = "name";

    private final @Nonnull String id;
    private final @Nonnull String name;

    public User(@Nonnull String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(@Nonnull JsonObject jsonObject) {
        id = jsonObject.getString(ID);
        name = jsonObject.getString(NAME);
    }

    public JsonObject encodeBody() {
        return Json.createObjectBuilder()
                .add(ID, id)
                .add(NAME, name)
                .build();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
