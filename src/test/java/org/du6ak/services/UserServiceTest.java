package org.du6ak.services;

import org.du6ak.models.Reading;
import org.du6ak.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";
    private static final User USER = new User(USERNAME, PASSWORD);

    @Test
    void testGetUsers() {
        // Arrange
        User user1 = new User("user1", "password", false);
        User user2 = new User("user2", "password", false);
        Set<Reading> readings1 = new HashSet<>();
        Set<Reading> readings2 = new HashSet<>();
        readings1.add(new Reading("device1", 1234L, 100, 1));
        readings2.add(new Reading("device2", 1234L, 200, 2));
        UserService.getUsers().put(user1, readings1);
        UserService.getUsers().put(user2, readings2);

        // Act
        Map<User, Set<Reading>> result = UserService.getUsers();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsKey(user1));
        assertTrue(result.containsKey(user2));
        assertEquals(readings1, result.get(user1));
        assertEquals(readings2, result.get(user2));
        UserService.getUsers().clear();
        UserService.setCurrentUser(null);
    }


    @Test
    void shouldReturnNullWhenNoUserIsLoggedIn() {
        // Arrange
        UserService.setCurrentUser(null);

        // Act
        User user = UserService.getCurrentUser();

        // Assert
        Assertions.assertNull(user);
    }

    @Test
    void shouldReturnTheCurrentlyLoggedInUser() {
        // Arrange
        User user = new User(USERNAME, PASSWORD);
        UserService.setCurrentUser(user);

        // Act
        User returnedUser = UserService.getCurrentUser();

        // Assert
        Assertions.assertEquals(user, returnedUser);
        UserService.setCurrentUser(null);
    }

    @Test
    void setCurrentUser() {
        // Arrange
        User user = new User("test", "test");

        // Act
        UserService.setCurrentUser(user);

        // Assert
        assertEquals(user, UserService.getCurrentUser());
        UserService.getUsers().clear();
        UserService.setCurrentUser(null);
    }

    @Test
    public void testRegistration() throws Exception {
        // Act
        UserService.registration(USERNAME, PASSWORD);

        // Assert
        Assertions.assertEquals(1, UserService.getUsers().size());
        User user = UserService.getUsers().keySet().stream().filter(u -> u.getUsername().equals(USERNAME)).findFirst().get();
        Assertions.assertEquals(USERNAME, user.getUsername());
        Assertions.assertEquals(PASSWORD, user.getPassword());
        UserService.getUsers().clear();
        UserService.setCurrentUser(null);
    }

    @Test
    void login_notRegisteredUser_throwsException() {
        Exception exception = assertThrows(Exception.class, () -> UserService.login("nonExistingUser", PASSWORD));
        assertEquals("Пользователь nonExistingUser не найден в базе.\nПожалуйста, пройдите регистрацию!", exception.getMessage());
    }


    @Test
    void login_nonExistingUser_throwsException() {
        UserService.getUsers().put(USER, new HashSet<>());
        Exception exception = assertThrows(Exception.class, () -> UserService.login("nonExistingUser", PASSWORD));
        assertEquals("Неправильный логин или пароль!\nПопробуйте еще раз", exception.getMessage());
        UserService.getUsers().clear();
    }

    @Test
    void login_incorrectPassword_throwsException() {
        UserService.getUsers().put(USER, new HashSet<>());
        Exception exception = assertThrows(Exception.class, () -> UserService.login(USERNAME, "incorrectPassword"));
        assertEquals("Неправильный логин или пароль!\nПопробуйте еще раз", exception.getMessage());
        UserService.getUsers().clear();
    }

    @Test
    void login_existingUser_setsCurrentUser() throws Exception {
        UserService.getUsers().put(USER, new HashSet<>());
        UserService.login(USERNAME, PASSWORD);

        Assertions.assertEquals(USER, UserService.getCurrentUser());
        UserService.getUsers().clear();
        UserService.setCurrentUser(null);
    }

    @Test
    public void testLogout() throws Exception {
        // Arrange
        UserService.registration(USERNAME, PASSWORD);
        UserService.login(USERNAME, PASSWORD);

        // Act
        UserService.logout();

        // Assert
        assertNull(UserService.getCurrentUser());
        UserService.getUsers().clear();
        UserService.setCurrentUser(null);
    }

    @Test
    void isRegistered_existingUser_returnsUser() throws Exception {
        // Arrange
        UserService.registration(USERNAME, PASSWORD);

        // Act
        User result = UserService.isRegistered(USERNAME);

        // Assert
        Assertions.assertEquals(USERNAME, result.getUsername());
        UserService.getUsers().clear();
        UserService.setCurrentUser(null);
    }

    @Test
    void isRegistered_nonExistingUser_returnsNull() {

        // Act
        User result = UserService.isRegistered(USERNAME);

        // Assert
        Assertions.assertNull(result);
    }
}