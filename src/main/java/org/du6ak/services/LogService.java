package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.User;

import java.util.*;

@Data
public class LogService {
    private static final Map<User, Queue<Log>> usersLogs = new HashMap<>();   // Логи пользователей

    public static Map<User, Queue<Log>> getUsersLogs() {
        return usersLogs;
    }

    // Записываем лог
    public static void addLog(User user, Log log) {
        if (usersLogs.containsKey(user)) {
            usersLogs.get(user).add(log);
        } else {
            usersLogs.put(user, new LinkedList<>());
            usersLogs.get(user).add(log);
        }
    }
}
