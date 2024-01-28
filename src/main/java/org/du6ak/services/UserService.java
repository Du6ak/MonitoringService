package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.User;

import java.util.*;

import static org.du6ak.services.LogService.*;

/**
 * This class provides services for managing users.
 */
@Data
public class UserService {

    /**
     * A map of users, where the key is the user and the value is a set of readings that the user has access to.
     */
    private static final HashMap<User, Set<Reading>> users = new HashMap<>();

    /**
     * A list of roles that are considered to be administrative.
     */
    private static final List<String> ROLES = List.of("admin", "administrator", "админ", "администратор");

    /**
     * The currently logged in user.
     */
    private static User currentUser;

    /**
     * Returns a map of all users and their readings.
     *
     * @return a map of users and their readings
     */
    public static Map<User, Set<Reading>> getUsers() {
        return users;
    }

    /**
     * Returns the currently logged in user.
     *
     * @return the currently logged in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently logged in user.
     *
     * @param newUser the new user to log in
     */
    public static void setCurrentUser(User newUser) {
        UserService.currentUser = newUser;
    }

    /**
     * Registers a new user.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @throws Exception if the user is already registered or there is an error registering the user
     */
    public static void registration(String username, String password) throws Exception {
        if (currentUser != null) {
            throw new Exception("Невозможно зарегистрироваться!\nВы уже авторизованы как " + currentUser.getUsername() + "!");
        }
        User newUser = new User(username, password, ROLES.contains(username.toLowerCase()));
        if (users.keySet().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            throw new Exception("Такой пользователь уже зарегистрирован!\nПопробуйте еще раз");
        }
        users.put(newUser, new HashSet<>());
        addLog(newUser, new Log("зарегистрировался"));
    }

    /**
     * Logs in an existing user.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @throws Exception if the user is not registered or the password is incorrect
     */
    public static void login(String username, String password) throws Exception {
        if (currentUser != null) {
            throw new Exception("Вы уже авторизованы!");
        }
        if (users.isEmpty()) {
            throw new Exception("Пользователь " + username + " не найден в базе.\nПожалуйста, пройдите регистрацию!");
        }
        currentUser = users.keySet().stream().filter(user -> user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)).findFirst()
                .orElseThrow(() -> new Exception("Неправильный логин или пароль!\nПопробуйте еще раз"));
        addLog(currentUser, new Log("авторизировался"));
    }

    /**
     * Logs out the currently logged in user.
     *
     * @throws Exception if the user is not logged in
     */
    public static void logout() throws Exception {
        if (currentUser == null) {
            throw new Exception("Вы не авторизованы!");
        }
        addLog(currentUser, new Log("вышел из учетной записи"));
        setCurrentUser(null);
    }

    /**
     * Checks if a user is registered.
     *
     * @param targetUsername the username of the user to check
     * @return the user if they are registered, or null if they are not registered
     */
    public static User isRegistered(String targetUsername) {
        return users.keySet().stream().filter(user -> user.getUsername().equalsIgnoreCase(targetUsername)).findFirst().orElse(null);
    }

    /**
     * Exits the program.
     */
    public static void exit() {
        System.exit(1);
    }

}
