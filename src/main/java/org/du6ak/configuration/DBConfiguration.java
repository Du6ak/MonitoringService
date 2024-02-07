package org.du6ak.configuration;

import org.du6ak.services.exceptions.ConnectionProblemsException;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * DBConfiguration class is used to manage database connections and statements.
 * It provides a single instance of the class that can be accessed through the getInstance() method.
 * The class loads the properties from the config.properties file and stores them in the PROPERTIES field.
 * The getStatement() and getConnection() methods are used to get a Statement and a Connection, respectively.
 */
public class DBConfiguration {

    private static final DBConfiguration INSTANCE = new DBConfiguration();

    /**
     * Returns the single instance of the DBConfiguration class.
     */
    public static DBConfiguration getInstance() {
        return INSTANCE;
    }

    private static final Properties PROPERTIES = new Properties();

    private static String host;
    private static String login;
    private static String password;

    /*
      Reads the properties from the config.properties file and stores them in the PROPERTIES field.
     */
    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            PROPERTIES.load(fis);
            host = PROPERTIES.getProperty("db.host");
            login = PROPERTIES.getProperty("db.login");
            password = PROPERTIES.getProperty("db.password");

        } catch (IOException e) {
            System.out.println("Файл config.properties не найден!\n" + e.getMessage());
        }
    }

    /**
     * Returns a Statement object that can be used to execute SQL queries.
     *
     * @throws ConnectionProblemsException if there are problems connecting to the database.
     */
    public Statement getStatement() throws ConnectionProblemsException {
        try {
            return DriverManager.getConnection(host, login, password).createStatement();
        } catch (SQLException e) {
            throw new ConnectionProblemsException("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    /**
     * Returns a Connection object that can be used to communicate with the database.
     *
     * @throws ConnectionProblemsException if there are problems connecting to the database.
     */
    public Connection getConnection() throws ConnectionProblemsException {
        try {
            return DriverManager.getConnection(host, login, password);
        } catch (SQLException e) {
            throw new ConnectionProblemsException("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }
}
