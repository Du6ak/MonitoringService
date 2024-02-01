package org.du6ak.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that represents a log.
 */
@Data
public class Log {

    /**
     * The date and time of the log entry.
     */
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    /**
     * The action that was taken.
     */
    private String date;
    private String action;

    /**
     * Creates a new log entry with the given action.
     *
     * @param action the action that was taken
     */
    public Log(String action) {
        this.date = LocalDateTime.now().format(FORMATTER);
        this.action = action;
    }
    private static Log getInstance(String action) {
        return new Log(action);
    }
}
