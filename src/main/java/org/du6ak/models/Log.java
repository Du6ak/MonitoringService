package org.du6ak.models;

import lombok.Data;
import org.du6ak.configuration.DBConfiguration;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a log.
 */
@Data
public class Log {
    private static final Log INSTANCE = new Log();

    /**
     * Returns the single instance of the Log class.
     *
     * @return the single instance of the Log class
     */
    public static Log getInstance() {
        return INSTANCE;
    }

    /**
     * The database configuration.
     */
    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();

    /**
     * Adds a log to the database.
     *
     * @param userId  the ID of the user who performed the action
     * @param logText the text of the log entry
     * @throws SQLException if there is an error adding the log to the database
     */
    public void addLog(int userId, String logText) throws SQLException {
        Connection connection = DB_CONFIG.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO entity.logs (user_id, log_date, log_text) VALUES (?, ?, ?)");
        {
            statement.setInt(1, userId);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(3, logText);
            statement.executeUpdate();
        }
    }

    /**
     * Returns a list of logs for a given user.
     *
     * @param username the username of the user
     * @param userId   the ID of the user
     * @return a list of logs for the given user
     * @throws SQLException if there is an error retrieving the logs from the database
     */
    public List<String> getLogs(String username, int userId) throws SQLException {
        String sql = "SELECT * FROM entity.logs WHERE user_id ='" + userId + "'";
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        List<String> userLogs = new ArrayList<>();
        while (rs.next()) {
            userLogs.add("Время -> " + rs.getTimestamp("log_date").toString() + ", действие -> " + username + " " + rs.getString("log_text"));
        }
        return userLogs;
    }
}
