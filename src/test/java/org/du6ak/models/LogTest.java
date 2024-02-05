package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogTest {
    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();
    private static final int TEST_USER_ID = 1;
    private static final String TEST_LOG_TEXT = "This is a test log entry.";

    private Log log;

    @BeforeEach
    void setUp() throws SQLException {
        log = Log.getInstance();
        Connection connection = DB_CONFIG.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM entity.logs");
        statement.executeUpdate();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = DB_CONFIG.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM entity.logs");
        statement.executeUpdate();
    }

    @Test
    void addLog() throws SQLException {
        log.addLog(TEST_USER_ID, TEST_LOG_TEXT);
        List<String> userLogs = log.getLogs("test", TEST_USER_ID);
        assertEquals(1, userLogs.size());
        String logEntry = userLogs.get(0);
        assertTrue(logEntry.contains(TEST_USER_ID + ""));
        assertTrue(logEntry.contains(TEST_LOG_TEXT));
    }
}
