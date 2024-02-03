package org.du6ak.services.out;

import org.du6ak.configuration.DBConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


/**
 * A utility class for writing to the console.
 */
public class ConsoleWriterService {
    private static final ConsoleWriterService INSTANCE = new ConsoleWriterService();

    public static ConsoleWriterService getInstance() {
        return INSTANCE;
    }

    public static final DBConfiguration DB_CONFIG = DBConfiguration.getInstance();
    /**
     * The ANSI escape code for red text.
     */
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * The ANSI escape code for resetting the text color.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Prints a list of strings to the console, with each string on a new line.
     *
     * @param strings the strings to print
     */
    public void printStrings(String... strings) {
        if (strings.length == 0) {
            return;
        }
        for (String str : strings) {
            System.out.println(ANSI_RESET + str + ANSI_RESET);
        }
    }

    /**
     * Prints a list to the console, with each string on a new line.
     *
     * @param strings the strings to print
     */
    public void printList(List<String> strings) {
        for (String string : strings) {
            printStrings(string);
        }
    }

    /**
     * Prints a list of errors to the console, with each error in red text.
     *
     * @param strings the errors to print
     */
    public void printErrors(String... strings) {
        if (strings.length == 0) {
            return;
        }
        for (String str : strings) {
            System.out.println(ANSI_RED + str + ANSI_RED);
        }
    }

    /**
     * Prints the list of roles to the console, with each role on a new line, along with its ID.
     *
     * @throws SQLException if there is an error while executing the SQL query
     */
    public void printRolesWithId() throws SQLException {
        String sql = "SELECT * FROM service.roles;";
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        while (rs.next()) {
            printStrings(rs.getString("id") + ". " + rs.getString("name"));
        }
    }

    /**
     * Prints the list of reading types to the console, with each type on a new line, along with its ID.
     *
     * @throws SQLException if there is an error while executing the SQL query
     */
    public void printTypesWithId() throws SQLException {
        String sql = "SELECT * FROM service.reading_types;";
        ResultSet rs = DB_CONFIG.getStatement().executeQuery(sql);
        while (rs.next()) {
            printStrings(rs.getString("id") + ". " + rs.getString("name"));
        }
    }

    /**
     * Prints the list of months to the console, with each month on a new line, along with its index (starting from 1).
     */
    public void printMonthsWithId() {
        List<Month> months = Arrays.asList(Month.values());
        for (int i = 0; i < months.size(); i++) {
            printStrings((i + 1) + ". " + months.get(i));
        }
    }

}
