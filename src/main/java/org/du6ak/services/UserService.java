package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.User;
import org.du6ak.repositories.Logs;
import org.du6ak.repositories.Roles;
import org.du6ak.repositories.Users;
import org.du6ak.services.exceptions.*;

/**
 * This class provides services for managing users.
 */
@Data
public class UserService {
    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance() {
        return INSTANCE;
    }

    private Users users = Users.getInstance();
    private Logs logs = Logs.getInstance();

    /**
     * The currently logged in user.
     */
    private User currentUser;


    /**
     * Returns the currently logged in user.
     *
     * @return the currently logged in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Registers a new user.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @throws Exception if the user is already registered or there is an error registering the user
     */
    public void registration(String username, String password, Roles role) throws Exception {
        if (currentUser != null) {
            throw new AlreadyAuthorizatedException(currentUser);
        }
        User newUser = new User(username, password, role);
        if (users.getUsers().keySet().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            throw new UserAlreadyExistsException();
        }
        users.addUser(newUser);
        logs.addLog(newUser, new org.du6ak.models.Log("зарегистрировался"));
    }

    /**
     * Logs in an existing user.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @throws Exception if the user is not registered or the password is incorrect
     */
    public void login(String username, String password) throws Exception {
        if (currentUser != null) {
            throw new AlreadyAuthorizatedException(currentUser);
        }
        if (users.getUsers().keySet().stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            throw new UserNotFoundException();
        }
        currentUser = users.getUsers().keySet().stream().filter(user -> user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)).findFirst()
                .orElseThrow(IncorrectDataException::new);
        logs.addLog(currentUser, new org.du6ak.models.Log("авторизировался"));
    }

    /**
     * Logs out the currently logged in user.
     *
     * @throws Exception if the user is not logged in
     */
    public void logout() throws Exception {
        if (currentUser == null) {
            throw new NotAuthorizatedException();
        }
        logs.addLog(currentUser, new org.du6ak.models.Log("вышел из учетной записи"));
        currentUser = null;
    }

    /**
     * Checks if a user is registered.
     *
     * @param targetUsername the username of the user to check
     * @return the user if they are registered, or null if they are not registered
     */
    public User isRegistered(String targetUsername) {
        return users.getUsers().keySet().stream().filter(user -> user.getUsername().equalsIgnoreCase(targetUsername)).findFirst().orElse(null);
    }

    /**
     * Exits the program.
     */
    public void exit() {
        System.exit(1);
    }

}
