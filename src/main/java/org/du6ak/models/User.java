package org.du6ak.models;

import lombok.Data;

/**
 * A class that represents a user in the system.
 */
@Data
public class User {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * A flag indicating whether the user is an administrator.
     */
    private boolean isAdmin;

    /**
     * Creates a new user with the specified username and password.
     * The user is not an administrator by default.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }

    /**
     * Creates a new user with the specified username, password, and administrator status.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param isAdmin  a flag indicating whether the user is an administrator
     */
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
