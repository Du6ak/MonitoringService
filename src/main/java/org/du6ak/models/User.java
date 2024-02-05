package org.du6ak.models;

import lombok.Data;
import org.du6ak.configuration.DBConfiguration;
import org.du6ak.services.exceptions.UserNotFoundException;

import java.sql.*;

/**
 * A class that represents a user in the system.
 */
@Data
public class User {
    private static final User INSTANCE = new User();

    /**
     * Returns the only instance of the User class.
     *
     * @return the only instance of the User class
     */
    public static User getInstance() {
        return INSTANCE;
    }

    private static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();

    /**
     * Checks if the system contains a user with the given username.
     *
     * @param targetUsername the username to check
     * @return true if the system contains a user with the given username, false otherwise
     * @throws SQLException if there is an error while executing the SQL query
     */
    public boolean isContains(String targetUsername) throws SQLException {
        Statement statement = DB_CONFIG.getStatement();
        String sql = "SELECT * FROM entity.users WHERE LOWER(name) = '" + targetUsername.toLowerCase() + "';";
        ResultSet rs = statement.executeQuery(sql);
        return rs.next();
    }

    /**
     * Adds a user to the system.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role of the user (1 for admin, 2 for regular user)
     * @throws SQLException if there is an error while executing the SQL query
     */
    public void addUser(String username, String password, int role) throws SQLException {
        Connection connection = DB_CONFIG.getConnection();
        String sql = "INSERT INTO entity.users (name, password, role_id) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, role);
        }
        statement.executeUpdate();
    }

    /**
     * Returns the ID of the user with the given username.
     *
     * @param username the username of the user
     * @return the ID of the user with the given username
     * @throws SQLException if there is an error while executing the SQL query
     */
    public int getUserId(String username) throws SQLException, UserNotFoundException {
        Statement statement = DB_CONFIG.getStatement();
        String sql = "SELECT id FROM entity.users WHERE LOWER(name) = '" + username.toLowerCase() + "';";
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()){
            return rs.getInt(1);
        }
        throw new UserNotFoundException();
    }

    /**
     * Returns the role of the user with the given username.
     *
     * @param username the username of the user
     * @return the role of the user with the given username
     * @throws SQLException          if there is an error while executing the SQL query
     * @throws UserNotFoundException if the user with the given username does not exist
     */
    public int getUserRole(String username) throws SQLException, UserNotFoundException {
        Statement statement = DB_CONFIG.getStatement();
        String sql = "SELECT role_id FROM entity.users WHERE LOWER(name) = '" + username.toLowerCase() + "';";
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        throw new UserNotFoundException();
    }

    /**
     * Checks if the given username and password match an existing user in the system.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the given username and password match an existing user in the system, false otherwise
     * @throws SQLException if there is an error while executing the SQL query
     */
    public boolean getUser(String username, String password) throws SQLException {
        Statement statement = DB_CONFIG.getStatement();
        String sql = "SELECT * FROM entity.users WHERE LOWER(name) = '" + username.toLowerCase() + "' AND password = '" + password + "';";
        ResultSet rs = statement.executeQuery(sql);
        return rs.next();
    }
}
