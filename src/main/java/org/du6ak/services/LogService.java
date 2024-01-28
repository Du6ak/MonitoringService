package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.User;

import java.util.*;

/**
 * This class provides methods for managing user logs.
 */
@Data
public class LogService {

    /**
     * A map of users and their logs.
     */
    private static final Map<User, Queue<Log>> usersLogs = new HashMap<>();

    /**
     * Returns a map of users and their logs.
     *
     * @return a map of users and their logs
     */
    public static Map<User, Queue<Log>> getUsersLogs() {
        return usersLogs;
    }

    /**
     * Adds a log to the system.
     *
     * @param user the user who created the log
     * @param log  the log to add
     */
    public static void addLog(User user, Log log) {
        if (usersLogs.containsKey(user)) {
            usersLogs.get(user).add(log);
        } else {
            usersLogs.put(user, new LinkedList<>());
            usersLogs.get(user).add(log);
        }
    }
}