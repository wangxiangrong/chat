package ru.ifmo.ctddev.koval.chat.cdi;

import ru.ifmo.ctddev.koval.chat.model.pojo.user.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Named
public class UserManager {

    private static final @Nonnull ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final @Nonnull Random random = new Random();

    private static final @Nonnull String[] names = {
            "Rick", "Brett", "Kelly", "Linette", "Lamont",
            "Lily", "Rachell", "Elisa", "Tonia", "Kareem"
    };
    private static final @Nonnull String[] images = {
            "https://33.media.tumblr.com/avatar_8d2647394efe_128.png",
            "http://38.media.tumblr.com/avatar_14ee6ada72a4_128.png",
            "http://www.little-dene.co.uk/grey%20tabby%20cat%20portrait.JPG",
            "https://31.media.tumblr.com/avatar_e8973c10573a_128.png"
    };
    private static final @Nonnull String[] levels = {"n", "m", "v"};
    private static final @Nonnull String[] tags = {"AAPL", "GOOG", "MSFT", "YHOO", "IBM", "MSFT", "FB", "LOL"};

    @Nullable
    public User getUserById(@Nonnull String id) {
        if (!users.containsKey(id)) {
            final String[] tagsArray = new String[random.nextInt(3)];
            for (int i = 0; i < tagsArray.length; i++) {
                tagsArray[i] = tags[random.nextInt(tags.length)];
            }
            final User user = new User(
                    id,
                    random.nextInt(10),
                    names[random.nextInt(names.length)],
                    levels[random.nextInt(levels.length)],
                    tagsArray,
                    images[random.nextInt(images.length)]
            );
            users.putIfAbsent(id, user);
        }
        return users.get(id);
    }

    @Nonnull
    public Collection<User> getUserProfiles(@Nonnull String[] profileIds) {
        final List<User> list = new ArrayList<>(profileIds.length);
        for (String id : profileIds) {
            list.add(getUserById(id));
        }
        return list;
    }

    public void updateUserProfile(@Nonnull User user) {
        users.put(user.getId(), user);
    }
}
