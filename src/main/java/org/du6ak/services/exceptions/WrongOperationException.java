package org.du6ak.services.exceptions;

/**
 * This exception is thrown when the given operation number is incorrect.
 */
public class WrongOperationException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     */
    public WrongOperationException() {
        super("Неверный номер операции!");
    }
}
