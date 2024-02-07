package org.du6ak.services.exceptions;

public class AlreadyAuthorizatedException extends Exception {
    public AlreadyAuthorizatedException(String authorizedUser) {
        super("Вы уже авторизованы как " + authorizedUser + "!");
    }
}
