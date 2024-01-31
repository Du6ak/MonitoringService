package org.du6ak.services.in;

import org.du6ak.services.exceptions.WrongOperationException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class provides static methods for reading input from the console.
 */
public abstract class ConsoleReaderService {

    /**
     * Reads a string from the console.
     *
     * @return the string that was read from the console
     */
    public static String readString() {
        return new Scanner(System.in).nextLine();
    }

    /**
     * Reads an integer from the console.
     *
     * @return the integer that was read from the console
     * @throws WrongOperationException if the user enters an invalid integer
     */
    public static int readInt() throws WrongOperationException {
        try {
            return new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            throw new WrongOperationException();
        }
    }

    /**
     * Reads a long from the console.
     *
     * @return the long that was read from the console
     * @throws WrongOperationException if the user enters an invalid long
     */
    public static Long readLong() throws WrongOperationException {
        try {
            return new Scanner(System.in).nextLong();
        } catch (InputMismatchException e) {
            throw new WrongOperationException();
        }
    }

    abstract Scanner getScanner();
}
