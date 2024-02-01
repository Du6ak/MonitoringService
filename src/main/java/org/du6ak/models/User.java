package org.du6ak.models;

import lombok.Data;
import org.du6ak.repositories.Roles;

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
    private Roles role;

    /**
     * A constructor that initializes the user object with the given username, password, and role.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role of the user
     */
    public User(String username, String password, Roles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
