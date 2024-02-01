package org.du6ak.services.exceptions;

public class IncorrectDataException extends Exception{
    public IncorrectDataException() {
        super("Проверьте правильность ввода");
    }
}
