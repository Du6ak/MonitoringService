package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.du6ak.services.exceptions.AlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ReadingTypeTest {
    private static final String NAME = "Test reading type";

    @AfterEach
    void tearDown() throws SQLException {
        String sql = "DELETE FROM service.reading_types WHERE service.reading_types.name ='"+NAME+"'";
        DBConfiguration.getInstance().getStatement().executeUpdate(sql);
    }
    @Test
    void isContains_WhenNameExists_ShouldReturnTrue() throws SQLException, AlreadyExistsException {
        // Arrange
        ReadingType readingType = ReadingType.getInstance();
        readingType.addReadingType(NAME);

        // Act
        boolean result = readingType.isContains(NAME);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    void isContains_WhenNameDoesNotExist_ShouldReturnFalse() throws SQLException {
        // Arrange
        ReadingType readingType = ReadingType.getInstance();

        // Act
        boolean result = readingType.isContains(NAME);

        // Assert
        Assertions.assertFalse(result);
    }
}
