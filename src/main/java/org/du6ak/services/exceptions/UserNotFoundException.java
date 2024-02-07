package org.du6ak.services.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Пользователь не найден!");
    }
}
