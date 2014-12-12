package ru.ifmo.ctddev.koval.chat.model.pojo.user;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class User {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CONTACTS = "contacts";
    private static final String EXPERIENCE = "exp";
    private static final String TAGS = "tags";
    private static final String IMG = "img";

    @Nonnull
    private final String id;
    private int contacts;
    private String name;
    private String experienceLevel;
    private String[] tags;
    private String imageUrl;

    public User(@Nonnull String id, int contacts, String name, String experienceLevel, String[] tags, String imageUrl) {
        this.id = id;
        this.contacts = contacts;
        this.name = name;
        this.experienceLevel = experienceLevel;
        this.tags = tags;
        this.imageUrl = imageUrl;
    }

    public User(@Nonnull JsonObject jsonObject) {
        id = jsonObject.getString(ID);
        name = jsonObject.getString(NAME);
        contacts = jsonObject.getInt(CONTACTS);
        experienceLevel = jsonObject.getString(EXPERIENCE);
        imageUrl = jsonObject.getString(IMG);

        final JsonArray tagsArray = jsonObject.getJsonArray(TAGS);
        tags = new String[tagsArray.size()];
        for (int i = 0; i < tagsArray.size(); i++) {
            tags[i] = tagsArray.getString(i);
        }

    }

    public JsonObject encodeBody() {
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String tag : tags) {
            arrayBuilder.add(tag);
        }
        return Json.createObjectBuilder().
                add(ID, id).
                add(NAME, name).
                add(CONTACTS, contacts).
                add(EXPERIENCE, experienceLevel).
                add(IMG, imageUrl).
                add(TAGS, arrayBuilder.build()).
                build();
    }

    public String getId() {
        return id;
    }

    public int getContacts() {
        return contacts;
    }

    public String getName() {
        return name;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String[] getTags() {
        return tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setContacts(int contacts) {
        this.contacts = contacts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
