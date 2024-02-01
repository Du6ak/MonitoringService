package org.du6ak.repositories;

import org.du6ak.models.Reading;
import org.du6ak.models.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Users {

    private static final Users INSTANCE = new Users();

    public static Users getInstance() {
        return INSTANCE;
    }

    /**
     * A map of users, where the key is the user and the value is a set of readings that the user has access to.
     */
    private final HashMap<User, Set<Reading>> users = new HashMap<>();

    public HashMap<User, Set<Reading>> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.putIfAbsent(user, new HashSet<>());
    }
}
