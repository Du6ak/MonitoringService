package org.du6ak.services;

import org.du6ak.models.Log;
import org.du6ak.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminServiceTest {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";
    private static final User USER = new User(USERNAME, PASSWORD);

    @Test
    void testGetUserLog() throws Exception {
        // Arrange
        LogService.addLog(USER, new Log("This is a test log"));
        LogService.addLog(USER, new Log("This is a test log2"));

        // Act
        Queue<Log> userLogs = AdminService.getUserLog(USER);

        // Assert
        Assertions.assertEquals(2, userLogs.size());
        Assertions.assertEquals("This is a test log", userLogs.poll().getAction());
        Assertions.assertEquals("This is a test log2", userLogs.poll().getAction());
        UserService.getUsers().clear();
        LogService.getUsersLogs().remove(USER);
    }

    @Test
    void testGetUserLog_UserNotFound() throws Exception {
        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> AdminService.getUserLog(null));
        Assertions.assertEquals("Пользователь не найден!", exception.getMessage());
    }

    @Test
    void testGetUserLog_NoLogs() throws Exception {
        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> AdminService.getUserLog(USER));
        Assertions.assertEquals("В базе нет логов!", exception.getMessage());
    }

    @Test
    public void addReadingType_newTypeAlreadyExists_throwsException() throws Exception {
        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> AdminService.addReadingType("холодная вода"));
        Assertions.assertEquals("Такой тип счетчика уже есть!", exception.getMessage());
    }

    @Test
    public void addReadingType_newTypeDoesNotExist_addsType() throws Exception {
        // Arrange
        String newReadingType = "newType";

        // Act
        AdminService.addReadingType(newReadingType);

        // Assert
        Assertions.assertTrue(MeterReadingService.getReadingTypes().contains(newReadingType));
    }

    @Test
    public void deleteReadingType_withNullParameter_shouldThrowException() {
        // Act and Assert
        assertThrows(Exception.class, () -> AdminService.deleteReadingType(null));
    }

    @Test
    public void deleteReadingType_withExistingType_shouldRemoveType() throws Exception {
        // Arrange
        String typeToDelete = "testType";
        AdminService.addReadingType(typeToDelete);

        // Act
        AdminService.deleteReadingType(typeToDelete);

        // Assert
        assertFalse(MeterReadingService.getReadingTypes().contains(typeToDelete));
    }


}