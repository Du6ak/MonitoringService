package org.du6ak.services.in;

import org.du6ak.services.exceptions.WrongOperationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReaderService {

    public static String readString() {
        return new Scanner(System.in).nextLine();
    }

    public static int readInt() throws WrongOperationException {
        try{
            return new Scanner(System.in).nextInt();
        }catch (InputMismatchException e) {
            throw new WrongOperationException();
        }
    }

    public static Long readLong() throws WrongOperationException {
        try{
            return new Scanner(System.in).nextLong();
        }catch (InputMismatchException e) {
            throw new WrongOperationException();
        }
    }
}
