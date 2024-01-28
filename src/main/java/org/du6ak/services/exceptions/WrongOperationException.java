package org.du6ak.services.exceptions;

public class WrongOperationException extends Exception {
    public WrongOperationException() {
        super("Неверный номер операции!");
    }
}
