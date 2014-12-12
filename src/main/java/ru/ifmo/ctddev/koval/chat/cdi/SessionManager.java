package ru.ifmo.ctddev.koval.chat.cdi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.concurrent.ConcurrentHashMap;

@Named
public class SessionManager {

    private static final @Nonnull ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    @Nullable
    public String getUserIdByToken(@Nonnull String token) {
        if (!tokenMap.containsKey(token)) {
            tokenMap.putIfAbsent(token, token);
        }
        return tokenMap.get(token);
    }

}
