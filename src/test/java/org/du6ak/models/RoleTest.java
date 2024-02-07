package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class RoleTest {

    private final int roleId = 1;
    private final int testRoleId = 100;

    @Test
    void isContains_WhenRoleExists_ShouldReturnTrue() throws SQLException {
        // Arrange
        Role role = Role.getInstance();

        // Act
        boolean result = role.isContains(roleId);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    void isContains_WhenRoleDoesNotExist_ShouldReturnFalse() throws SQLException {
        // Arrange
        Role role = Role.getInstance();


        // Act
        boolean result = role.isContains(testRoleId);

        // Assert
        Assertions.assertFalse(result);
    }
}
