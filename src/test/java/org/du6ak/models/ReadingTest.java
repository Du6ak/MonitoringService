package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.du6ak.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Month;

public class ReadingTest {
    private static final int METER_TYPE_ID = 1;
    private static final int CONTRACT_NUMBER = 1;
    private static final int VALUE = 1;
    private static final int MONTH_NUMBER = 1;

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";

    private static final int USER_ROLE = 2;


    private Reading reading;
    private DBConfiguration dbConfiguration;
    private ReadingType readingType;
    private User user;

    @BeforeEach
    void setUp() throws SQLException, UserNotFoundException {
        reading = Reading.getInstance();
        user = User.getInstance();
        user.addUser(USERNAME, PASSWORD, USER_ROLE);
        int userId = user.getUserId(USERNAME);
        reading.addReading(userId, METER_TYPE_ID, CONTRACT_NUMBER, VALUE, MONTH_NUMBER);
    }

    @AfterEach
    void tearDown() throws SQLException, UserNotFoundException {
        String sql = "DELETE FROM entity.readings";
        DBConfiguration.getInstance().getStatement().executeUpdate(sql);

        String sql1 = "DELETE FROM entity.readings WHERE user_id = ? AND reading_type_id = ? AND month = ?";
        PreparedStatement statement = DBConfiguration.getInstance().getConnection().prepareStatement(sql1);
        int userId = user.getUserId(USERNAME);
        statement.setInt(1, userId);
        statement.setInt(2, METER_TYPE_ID);
        statement.setInt(3, MONTH_NUMBER);

        String sql2 = "DELETE FROM entity.users WHERE name = ? AND password = ? AND role_id = ?";
        PreparedStatement statement1 = DBConfiguration.getInstance().getConnection().prepareStatement(sql2);
        statement1.setString(1, USERNAME);
        statement1.setString(2, PASSWORD);
        statement1.setInt(3, USER_ROLE);
        statement1.executeUpdate();
    }

    @Test
    void isContainsShouldReturnTrueIfUserHasReadingsForGivenMeterTypeAndContractNumberAndMonth() throws SQLException, UserNotFoundException {
        int userId = user.getUserId(USERNAME);
        boolean result = reading.isContains(userId, METER_TYPE_ID, CONTRACT_NUMBER, VALUE, MONTH_NUMBER);
        Assertions.assertTrue(result);
    }

    @Test
    void isContainsShouldReturnFalseIfUserDoesNotHaveReadingsForGivenMeterTypeAndContractNumberAndMonth() throws SQLException, UserNotFoundException {
        int userId = user.getUserId(USERNAME);
        boolean result = reading.isContains(userId, 2, CONTRACT_NUMBER, VALUE, MONTH_NUMBER);
        Assertions.assertFalse(result);
    }

    @Test
    void addReading() throws SQLException, UserNotFoundException {
        int userId = user.getUserId(USERNAME);
        int typeId = 1;
        int contractNumber = 1;
        int value = 1;
        int monthNumber = 1;

        reading.addReading(userId, typeId, contractNumber, value, monthNumber);

        boolean contains = reading.isContains(userId, typeId, contractNumber, value, monthNumber);
        Assertions.assertTrue(contains);
    }

    @Test
    void testGetReading() throws SQLException, UserNotFoundException {
        int userId = user.getUserId(USERNAME);
        String expected = "\nНомер контракта: " + CONTRACT_NUMBER +
                "\nПоказания счетчика: " + VALUE +
                "\nМесяц: " + Month.of(MONTH_NUMBER).name() + "\n";
        String actual = reading.getReading(userId, METER_TYPE_ID);
        Assertions.assertEquals(expected, actual);
    }
}
