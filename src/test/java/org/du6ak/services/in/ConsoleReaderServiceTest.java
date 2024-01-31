package org.du6ak.services.in;

import org.du6ak.services.exceptions.WrongOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class ConsoleReaderServiceTest {

    @Test
    public void testReadString() {
        // Arrange
        String expectedString = "Test string";
        System.setIn(new ByteArrayInputStream(expectedString.getBytes()));

        // Act
        String actualString = ConsoleReaderService.readString();

        // Assert
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void testReadInt() throws WrongOperationException {
        // Arrange
        int expectedInt = 10;
        System.setIn(new ByteArrayInputStream(String.valueOf(expectedInt).getBytes()));

        // Act
        int actualInt = ConsoleReaderService.readInt();

        // Assert
        Assertions.assertEquals(expectedInt, actualInt);
    }
    @Test
    public void testReadLong() throws WrongOperationException {
        // Arrange
        long expectedLong = 10L;
        System.setIn(new ByteArrayInputStream(String.valueOf(expectedLong).getBytes()));

        // Act
        long actualLong = ConsoleReaderService.readLong();

        // Assert
        Assertions.assertEquals(expectedLong, actualLong);
    }
}
