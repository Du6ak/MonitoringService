package org.du6ak.services.out;

import org.du6ak.models.Log;
import org.du6ak.models.Reading;

import java.util.List;
import java.util.Queue;

import static org.du6ak.services.MeterReadingService.MONTHS;

/**
 * A utility class for writing to the console.
 */
public class ConsoleWriterService {

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
    public static void printStrings(String... strings) {
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
    public static void printReadings(List<Reading> readings) {
        for (var reading : readings) {
            printStrings(
                    "Счетчик: " + reading.getType() +
                            "\nНомер договора: " + reading.getContractNumber() +
                            "\nЗначение счетчика: " + reading.getValue() +
                            "\nМесяц подачи: " + MONTHS.get(reading.getMonth() - 1) + " (" + reading.getMonth() + ")\n"
            );
        }
    }

    /**
     * Prints a list of logs to the console, with each log on a new line.
     *
     * @param logs the logs to print
     */
    public static void printLogs(Queue<Log> logs) {
        for (var log : logs) {
            printStrings(log.getDate() + " " + log.getAction());
        }
    }

    /**
     * Prints a list of errors to the console, with each error in red text.
     *
     * @param strings the errors to print
     */
    public static void printErrors(String... strings) {
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
    public static void printStringsWithIndex(List<String> strings) {
        if (strings.isEmpty()) {
            return;
        }
        for (int i = 0; i < strings.size(); i++) {
            System.out.println((i + 1) + ". " + strings.get(i));
        }
    }
}
