package org.du6ak.services.in;

import org.du6ak.services.exceptions.IncorrectDataException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class provides static methods for reading input from the console.
 */
public class ConsoleReaderService {

    private static final ConsoleReaderService INSTANCE = new ConsoleReaderService();

    public static ConsoleReaderService getInstance() {
        return INSTANCE;
    }


    /**
     * Reads a string from the console.
     *
     * @return the string that was read from the console
     */
    public String readString() {
        return new Scanner(System.in).nextLine();
    }

    /**
     * Reads an integer from the console.
     *
     * @return the integer that was read from the console
     */
    public int readInt() throws IncorrectDataException {
        try {
            return new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            throw new IncorrectDataException();
        }
    }

}
