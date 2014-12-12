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

//TODO
@Named
public final class UserManager {

    private static final @Nonnull ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final @Nonnull Random random = new Random();

    private static final @Nonnull String[] names = {
            "Rick", "Brett", "Kelly", "Linette", "Lamont",
            "Lily", "Rachell", "Elisa", "Tonia", "Kareem"
    };

    @Nullable
    public User getUserById(@Nonnull String id) {
        if (!users.containsKey(id)) {
            final User user = new User(
                    id,
                    names[random.nextInt(names.length)]
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
