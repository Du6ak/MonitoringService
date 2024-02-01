package org.du6ak.services.out;

import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.repositories.Roles;
import org.du6ak.services.exceptions.IncorrectDataException;
import org.du6ak.services.exceptions.WrongOperationException;
import org.du6ak.services.in.ConsoleReaderService;

import java.util.List;
import java.util.Queue;


/**
 * A utility class for writing to the console.
 */
public class ConsoleWriterService {

    private static final ConsoleWriterService INSTANCE = new ConsoleWriterService();

    public static ConsoleWriterService getInstance() {
        return INSTANCE;
    }

    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();

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
     * Prints a list of readings to the console, with each reading on a new line.
     *
     * @param readings the readings to print
     */
    public void printReadings(List<Reading> readings) {
        for (var reading : readings) {
            printStrings(
                    "Счетчик: " + reading.getType() +
                            "\nНомер договора: " + reading.getContractNumber() +
                            "\nЗначение счетчика: " + reading.getValue() +
                            "\nМесяц подачи: " + reading.getMonth() + " (" + reading.getMonth().ordinal() + ")\n"
            );
        }
    }

    /**
     * Prints a list of logs to the console, with each log on a new line.
     *
     * @param logs the logs to print
     */
    public void printLogs(Queue<Log> logs) {
        for (var log : logs) {
            printStrings(log.getDate() + " " + log.getAction());
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
     * Prints a list of strings with their indices to the console, with each string on a new line and its index in parentheses.
     *
     * @param strings the strings to print
     */
    public void printStringsWithIndex(List<String> strings) throws IncorrectDataException {
        if (strings.isEmpty()) {
            throw new IncorrectDataException();
        }
        for (int i = 0; i < strings.size(); i++) {
            System.out.println((i + 1) + ". " + strings.get(i));
        }
    }

    public Roles printRolesWithIndex(List<Roles> roles) throws WrongOperationException {
        for (int i = 1; i <= roles.size(); i++) {
            printStrings(i + ". " + roles.get(i - 1));
        }
        int choice = consoleReaderService.readInt();
        if (choice < 1 || choice > roles.size()+1) {
            throw new WrongOperationException();
        }
        return roles.get(choice - 1);
    }

}
