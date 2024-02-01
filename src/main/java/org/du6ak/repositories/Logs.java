package org.du6ak.repositories;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.User;
import org.du6ak.services.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Data
public class Logs {

    private static Logs instance = new Logs();

    public static Logs getInstance() {
        return instance;
    }

    /**
     * A map of users and their logs.
     */
    private final Map<User, Queue<Log>> usersLogs = new HashMap<>();

    /**
     * Returns a map of users and their logs.
     *
     * @return a map of users and their logs
     */
    public Map<User, Queue<Log>> getUsersLogs() {
        return usersLogs;
    }

    public Queue<Log> getLogs(User user) throws UserNotFoundException {
        if (!usersLogs.containsKey(user)) {
            throw new UserNotFoundException();
        }
        return usersLogs.get(user);
    }

    public void addLog(User user, org.du6ak.models.Log log) {
        if (!usersLogs.containsKey(user)) {
            usersLogs.put(user, new LinkedList<>());
        }
        usersLogs.get(user).add(log);
    }
}
