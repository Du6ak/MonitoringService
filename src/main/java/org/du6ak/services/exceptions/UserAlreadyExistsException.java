package org.du6ak.services.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("Такой пользователь уже зарегистрирован!");
    }
}
