package org.du6ak.services;

import org.du6ak.models.Reading;
import org.du6ak.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.du6ak.services.MeterReadingService.addType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void sendReadings_ValidUser_ShouldAddReadingToUser() throws Exception {
        // Arrange
        User user = new User("John Doe", "johndoe", true);
        UserService.getUsers().put(user,new HashSet<>());
        String type = "электричество";
        Long contractNumber = 1234567890L;
        int value = 123;
        int month = 12;
        Reading newReading = new Reading(type, contractNumber, value, month);

        // Act
        MeterReadingService.sendReadings(user);

        // Assert
        Set<Reading> readings = UserService.getUsers().get(user);
        assertTrue(readings.contains(newReading));

    }

}
