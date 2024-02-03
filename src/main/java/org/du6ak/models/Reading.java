package org.du6ak.models;

import lombok.Data;
import org.du6ak.configuration.DBConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a reading from a meter.
 */
@Data
public class Reading {
    private static final Reading INSTANCE = new Reading();

    /**
     * Returns the single instance of the Reading class.
     *
     * @return the single instance of the Reading class
     */
    public static Reading getInstance() {
        return INSTANCE;
    }

    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();
    private static final ReadingType READING_TYPE = ReadingType.getInstance();

    /**
     * Checks if the given user has readings for the given meter type, contract number, and month.
     *
     * @param userId         the user ID
     * @param typeId         the meter type ID
     * @param contractNumber the contract number
     * @param value          the reading value
     * @param monthNumber    the month number
     * @return true if the user has readings for the given meter type, contract number, and month, false otherwise
     * @throws SQLException if there is an error executing the SQL query
     */
    public boolean isContains(int userId, int typeId, int contractNumber, int value, int monthNumber) throws SQLException {
        String sql =
                "SELECT * FROM entity.readings WHERE user_id ='" + userId + "' AND reading_type_id =" + typeId + " AND contract_number =" + contractNumber +
                        " AND value =" + value + " AND month =" + monthNumber;
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        return rs.next();
    }

    /**
     * Adds a new reading for the given user and meter type.
     *
     * @param userId         the user ID
     * @param typeId         the meter type ID
     * @param contractNumber the contract number
     * @param value          the reading value
     * @param monthNumber    the month number
     * @throws SQLException if there is an error executing the SQL query or adding the reading
     */
    public void addReading(int userId, int typeId, int contractNumber, int value, int monthNumber) throws SQLException {
        Connection connection = DB_CONFIG.getConnection();
        String sql = "INSERT INTO entity.readings (user_id, reading_type_id, contract_number, value, month) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        {
            statement.setInt(1, userId);
            statement.setInt(2, typeId);
            statement.setInt(3, contractNumber);
            statement.setInt(4, value);
            statement.setInt(5, monthNumber);
        }
        statement.executeUpdate();
    }

    /**
     * Returns the latest reading for the given user and meter type.
     *
     * @param userId the user ID
     * @param typeId the meter type ID
     * @return the latest reading for the given user and meter type, or null if the user does not have any readings for the given meter type
     * @throws SQLException if there is an error executing the SQL query
     */
    public String getReading(int userId, int typeId) throws SQLException {
        String sql = "SELECT * FROM entity.readings WHERE user_id =" + userId + " AND reading_type_id =" + typeId + " AND month = (SELECT MAX(month) FROM entity.readings WHERE reading_type_id =" + typeId + " AND user_id =" + userId + ")";
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        if (rs.next()) {
            return "\nНомер контракта: " + rs.getInt("contract_number") +
                    "\nПоказания счетчика: " + rs.getInt("value") +
                    "\nМесяц: " + Month.of(rs.getInt("month")).name() + "\n";
        }
        return null;
    }

    /**
     * Returns the readings for the given user, meter type, and month.
     *
     * @param username    the username
     * @param userId      the user ID
     * @param typeId      the meter type ID
     * @param monthNumber the month number
     * @return the readings for the given user, meter type, and month
     * @throws SQLException if there is an error executing the SQL query
     */
    public List<String> getReadings(String username, int userId, int typeId, int monthNumber) throws SQLException {
        String sql = "SELECT * FROM entity.readings WHERE user_id =" + userId + " AND reading_type_id =" + typeId + " AND month =" + monthNumber;
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        List<String> monthReadings = new ArrayList<>();
        while (rs.next()) {
            monthReadings.add("\nПользователь: " + username +
                    "\nТип счетчика: " + READING_TYPE.getTypeName(typeId) +
                    "\nНомер контракта: " + rs.getInt("contract_number") +
                    "\nПоказания счетчика: " + rs.getInt("value") +
                    "\nМесяц: " + Month.of(rs.getInt("month")).name() + "\n");
        }
        return monthReadings;
    }

    /**
     * Returns the readings for the given user.
     *
     * @param username the username
     * @param userId   the user ID
     * @return the readings for the given user
     * @throws SQLException if there is an error executing the SQL query
     */
    public List<String> getReadings(String username, int userId) throws SQLException {
        String sql = "SELECT * FROM entity.readings WHERE user_id ='" + userId + "'";
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        List<String> historyReadings = new ArrayList<>();
        while (rs.next()) {
            historyReadings.add("\nПользователь: " + username +
                    "\nТип счетчика: " + READING_TYPE.getTypeName(rs.getInt("reading_type_id")) +
                    "\nНомер контракта: " + rs.getInt("contract_number") +
                    "\nПоказания счетчика: " + rs.getInt("value") +
                    "\nМесяц: " + Month.of(rs.getInt("month")).name() + "\n");
        }
        return historyReadings;
    }
}