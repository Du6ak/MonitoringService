package org.du6ak.services.exceptions;

import org.du6ak.models.User;

public class AlreadyAuthorizatedException extends Exception {
    public AlreadyAuthorizatedException(User authorizatedUser) {
        super("Вы уже авторизованы как " + authorizatedUser.getUsername() + "!");
    }
}
