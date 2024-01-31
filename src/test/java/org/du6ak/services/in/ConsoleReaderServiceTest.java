package org.du6ak.services.in;

import org.du6ak.services.exceptions.WrongOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class ConsoleReaderServiceTest {

    @Test
    void readString_shouldReturnString() {
        // Arrange
        Scanner scanner = new Scanner("test");
        ConsoleReaderService consoleReaderService = new ConsoleReaderService() {
            @Override
            Scanner getScanner() {
                return scanner;
            }
        };

        // Act
        String result = consoleReaderService.readString();

        // Assert
        Assertions.assertEquals("test", result);
    }

    @Test
    void readInt_shouldReturnInteger() throws WrongOperationException {
        // Arrange
        Scanner scanner = new Scanner("123");
        ConsoleReaderService consoleReaderService = new ConsoleReaderService() {
            @Override
            Scanner getScanner() {
                return scanner;
            }
        };

        // Act
        int result = consoleReaderService.readInt();

        // Assert
        Assertions.assertEquals(123, result);
    }

    @Test
    void readLong_shouldReturnLong() throws WrongOperationException {
        // Arrange
        Scanner scanner = new Scanner("1234567890123456789L");
        ConsoleReaderService consoleReaderService = new ConsoleReaderService() {
            @Override
            Scanner getScanner() {
                return scanner;
            }
        };

        // Act
        Long result = consoleReaderService.readLong();

        // Assert
        Assertions.assertEquals(1234567890123456789L, result);
    }
}
