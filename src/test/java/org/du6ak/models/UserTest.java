package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.du6ak.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.junit.jupiter.api.AfterEach;

import java.sql.SQLException;

public class UserTest {

    private final String username = "testUser";
    private final String password = "password";
    private final int role = 2;
    private final User user = User.getInstance();

    @BeforeEach
    void setUp() throws SQLException {
        user.addUser(username, password, role);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String sql = "DELETE FROM entity.users WHERE name='" + username + "' AND password='" + password + "';";
        DBConfiguration.getInstance().getStatement().executeUpdate(sql);
    }

    @Test
    void addUser_shouldAddUserToDatabase_givenValidData() throws SQLException {
        boolean exists = user.isContains(username);
        Assertions.assertTrue(exists);
    }

    @Test
    void isContains_shouldReturnTrueIfUserExists_givenValidUsername() throws SQLException {
        boolean result = user.isContains(username);
        Assertions.assertTrue(result);
    }

    @Test
    void getUserRole_shouldReturnUserRole_givenValidUsername() throws SQLException, UserNotFoundException {
        int role = user.getUserRole(username);
        Assertions.assertNotEquals(0, role);
    }

    @Test
    void getUserId_shouldReturnUserId_givenValidUsername() throws SQLException, UserNotFoundException {
        int userId = user.getUserId(username);
        Assertions.assertNotEquals(0, userId);
    }

    @Test
    void getUser_shouldReturnTrueIfUserExists_givenValidCredentials() throws SQLException {
        boolean exists = user.getUser(username, password);
        Assertions.assertTrue(exists);
    }

    @Test
    void isContains_shouldReturnFalseIfUserDoesNotExist_givenInvalidUsername() throws SQLException {
        String username = "invalidUsername";
        boolean result = user.isContains(username);
        Assertions.assertFalse(result);
    }

    @Test
    void getUserId_shouldThrowUserNotFoundException_givenInvalidUsername() {
        String username = "invalidUsername";
        Assertions.assertThrows(UserNotFoundException.class, () -> user.getUserId(username));
    }

    @Test
    void getUserRole_shouldThrowUserNotFoundException_givenInvalidUsername() {
        String username = "invalidUsername";
        Assertions.assertThrows(UserNotFoundException.class, () -> user.getUserRole(username));
    }


    @Test
    void getUser_shouldReturnFalseIfUserDoesNotExist_givenInvalidCredentials() throws SQLException {
        String username = "testUser";
        String password = "invalidPassword";
        boolean exists = user.getUser(username, password);
        Assertions.assertFalse(exists);
    }
}
