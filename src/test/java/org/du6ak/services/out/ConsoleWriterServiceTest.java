package org.du6ak.services.out;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ConsoleWriterServiceTest {

    @Test
    public void testPrintStringsWithIndex() {
        // Arrange
        List<String> strings = Arrays.asList("1. String 1", "2. String 2", "3. String 3");

        // Act
        ConsoleWriterService.printStringsWithIndex(strings);

        // Assert
        Assertions.assertEquals(3, strings.size());
        Assertions.assertEquals("1. String 1", strings.get(0));
        Assertions.assertEquals("2. String 2", strings.get(1));
        Assertions.assertEquals("3. String 3", strings.get(2));
    }
}