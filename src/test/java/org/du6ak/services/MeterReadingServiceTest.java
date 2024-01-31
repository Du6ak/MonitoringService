package org.du6ak.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeterReadingServiceTest {
    private static final String TYPE = "газ";

    @Test
    void testGetReadingTypes() {
        // Arrange
        List<String> expectedTypes = MeterReadingService.getReadingTypes();

        // Act
        List<String> actualTypes = MeterReadingService.getReadingTypes();

        // Assert
        Assertions.assertEquals(expectedTypes, actualTypes);
    }

    @Test
    public void addType_shouldAddTypeToTheList_whenTypeIsNotPresent() {
        // Act
        MeterReadingService.addType(TYPE);

        // Assert
        assertTrue(MeterReadingService.getReadingTypes().contains(TYPE));
    }

    @Test
    public void removeType_RemovesSpecifiedType_FromList() {
        // Act
        MeterReadingService.removeType(TYPE);
        // Assert
        assertFalse(MeterReadingService.getReadingTypes().contains(TYPE));
    }
}
