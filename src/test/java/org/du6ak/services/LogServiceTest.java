package org.du6ak.services;

import org.du6ak.models.Log;
import org.du6ak.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class LogServiceTest {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";
    private static final User USER = new User(USERNAME, PASSWORD);

    @Test
    void addLog() {
        Log log = new Log("This is a test log message.");

        LogService.addLog(USER, log);

        Map<User, Queue<Log>> usersLogs = LogService.getUsersLogs();
        assertTrue(usersLogs.containsKey(USER));
        Queue<Log> userLogs = usersLogs.get(USER);
        assertEquals(1, userLogs.size());
        assertEquals(log, userLogs.peek());
    }
}