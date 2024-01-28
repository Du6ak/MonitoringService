package org.du6ak.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Log {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
    private String date;
    private String action;

    public Log(String action) {
        this.date = LocalDateTime.now().format(FORMATTER);
        this.action = action;
    }
}
