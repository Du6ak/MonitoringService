package org.du6ak.services.exceptions;

public class DataNotFoundException extends Exception {
    public DataNotFoundException() {
        super("Не найдено данных!");
    }
}
