package org.du6ak.services.exceptions;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
        super("Уже существует!");
    }
}
