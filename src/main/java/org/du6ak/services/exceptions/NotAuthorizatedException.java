package org.du6ak.services.exceptions;

public class NotAuthorizatedException extends Exception {
    public NotAuthorizatedException() {
        super("Вы не авторизованы!");
    }
}
