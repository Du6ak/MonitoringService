package org.du6ak.services.in;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class ConsoleReaderServiceTest {
    @Test
    public void read_Int() throws Exception {
        System.out.println("Введите число:");
        int expected = readNumber();
        int actual = ConsoleReaderService.readInt();

        Assertions.assertEquals(expected, actual);
    }

    private static int readNumber() {
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();
        return -1;
    }

}
