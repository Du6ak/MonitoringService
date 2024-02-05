package org.du6ak.models;

import org.du6ak.configuration.DBConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;

public class ReadingTest {
    private static final int USER_ID = 1;
    private static final int METER_TYPE_ID = 1;
    private static final int CONTRACT_NUMBER = 1;
    private static final int VALUE = 1;
    private static final int MONTH_NUMBER = 1;

    private Reading reading;

    @BeforeEach
    void setUp() throws SQLException {
        reading = Reading.getInstance();
        reading.addReading(USER_ID, METER_TYPE_ID, CONTRACT_NUMBER, VALUE, MONTH_NUMBER);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String sql = "DELETE FROM entity.readings";
        DBConfiguration.getInstance().getStatement().executeUpdate(sql);
    }

    @Test
    void isContainsShouldReturnTrueIfUserHasReadingsForGivenMeterTypeAndContractNumberAndMonth() throws SQLException {
        boolean result = reading.isContains(USER_ID, METER_TYPE_ID, CONTRACT_NUMBER, VALUE, MONTH_NUMBER);
        Assertions.assertTrue(result);
    }

    @Test
    void isContainsShouldReturnFalseIfUserDoesNotHaveReadingsForGivenMeterTypeAndContractNumberAndMonth() throws SQLException {
        boolean result = reading.isContains(USER_ID, 2, CONTRACT_NUMBER, VALUE, MONTH_NUMBER);
        Assertions.assertFalse(result);
    }

    private DBConfiguration dbConfiguration;
    private ReadingType readingType;

    @BeforeEach
    void setUp1() throws SQLException {
        dbConfiguration = DBConfiguration.getInstance();
        readingType = ReadingType.getInstance();
        reading = Reading.getInstance();
    }

    @Test
    void addReading() throws SQLException {
        int userId = 1;
        int typeId = 1;
        int contractNumber = 1;
        int value = 1;
        int monthNumber = 1;

        reading.addReading(userId, typeId, contractNumber, value, monthNumber);

        boolean contains = reading.isContains(userId, typeId, contractNumber, value, monthNumber);
        Assertions.assertTrue(contains);
    }
    @AfterEach
    void tearDown1() throws SQLException {
        String sql = "DELETE FROM entity.readings WHERE user_id = ? AND reading_type_id = ? AND month = ?";
        PreparedStatement statement = DBConfiguration.getInstance().getConnection().prepareStatement(sql);
        statement.setInt(1, USER_ID);
        statement.setInt(2, METER_TYPE_ID);
        statement.setInt(3, MONTH_NUMBER);
        statement.executeUpdate();
    }

    @Test
    void testGetReading() throws SQLException {
        String expected = "\nНомер контракта: " + CONTRACT_NUMBER +
                "\nПоказания счетчика: " + VALUE +
                "\nМесяц: " + Month.of(MONTH_NUMBER).name() + "\n";
        String actual = reading.getReading(USER_ID, METER_TYPE_ID);
        Assertions.assertEquals(expected, actual);
    }
}
