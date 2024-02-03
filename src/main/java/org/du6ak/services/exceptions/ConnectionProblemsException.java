package org.du6ak.services.exceptions;

import java.sql.SQLException;

public class ConnectionProblemsException extends SQLException {
    public ConnectionProblemsException(String message) {
        super(message);
    }
}
