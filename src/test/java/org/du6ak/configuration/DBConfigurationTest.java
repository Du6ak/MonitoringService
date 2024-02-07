package org.du6ak.configuration;

import org.du6ak.services.exceptions.ConnectionProblemsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

public class DBConfigurationTest {

    @Test
    public void testGetStatement() throws ConnectionProblemsException {
        DBConfiguration dbConfiguration = DBConfiguration.getInstance();
        Statement statement = dbConfiguration.getStatement();
        Assertions.assertNotNull(statement);
    }

    @Test
    public void testGetConnection() throws ConnectionProblemsException {
        DBConfiguration dbConfiguration = DBConfiguration.getInstance();
        Connection connection = dbConfiguration.getConnection();
        Assertions.assertNotNull(connection);
    }
}
