package org.du6ak.services.out;

import org.du6ak.models.Log;
import org.du6ak.models.Reading;

import java.util.List;
import java.util.Queue;

import static org.du6ak.services.MeterReadingService.MONTHS;

public class ConsoleWriterService {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void printStrings(String... strings) {
        if (strings.length == 0) {
            return;
        }
        for (String str : strings) {
            System.out.println(ANSI_RESET + str + ANSI_RESET);
        }
    }

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

    public static void printLogs(Queue<Log> logs) {
        for (var log : logs) {
            printStrings(log.getDate() + " " + log.getAction());
        }
    }


    public static void printErrors(String... strings) {
        if (strings.length == 0) {
            return;
        }
        for (String str : strings) {
            System.out.println(ANSI_RED + str + ANSI_RED);
        }
    }

    public static void printStringsWithIndex(List<String> strings) {
        if (strings.isEmpty()) {
            return;
        }
        for (int i = 0; i < strings.size(); i++) {
            System.out.println((i + 1) + ". " + strings.get(i));
        }
    }
}
