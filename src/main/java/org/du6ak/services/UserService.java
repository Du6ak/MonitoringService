package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.User;
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

    private User user = User.getInstance();
    private Log log = Log.getInstance();

    /**
     * The currently logged in user.
     */
    private String currentUser;

    /**
     * This method registers a new user with the system.
     *
     * @param username the username of the new user
     * @param password the password of the <PASSWORD>
     * @param role     the role of the new user (1 for regular user, 2 for administrator)
     * @throws Exception if the user is already authorized, if the username is already in use, or if there was an error registering the user
     */
    public void registration(String username, String password, int role) throws Exception {
        if (currentUser != null) {
            throw new AlreadyAuthorizatedException(currentUser);
        }
        if (user.isContains(username)) {
            throw new UserAlreadyExistsException();
        }
        user.addUser(username, password, role);
        log.addLog(user.getUserId(username), "зарегистрировался");
    }

    /**
     * This method authenticates a user's login credentials.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @throws Exception if the user is already authenticated, if the username is not found, or if the password is incorrect
     */
    public void login(String username, String password) throws Exception {
        if (currentUser != null) {
            throw new AlreadyAuthorizatedException(currentUser);
        }
        if (!user.isContains(username) || username.isEmpty()) {
            throw new UserNotFoundException();
        }
        if (!user.getUser(username, password)) {
            throw new IncorrectDataException();
        }
        currentUser = username;
        log.addLog(user.getUserId(username), "авторизировался");
    }

    /**
     * The logout method is used to log the user out of the system.
     *
     * @param username is the name of the user who wants to log out.
     * @throws Exception If the current user is not installed.
     */
    public void logout(String username) throws Exception {
        if (currentUser == null) {
            throw new NotAuthorizatedException();
        }
        log.addLog(user.getUserId(username), "вышел из учетной записи");
        setCurrentUser(null);
    }

    /**
     * Exits the program.
     */
    public void exit() {
        System.exit(1);
    }

}
