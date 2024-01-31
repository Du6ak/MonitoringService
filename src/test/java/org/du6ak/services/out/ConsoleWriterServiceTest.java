package org.du6ak.services.out;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.du6ak.services.out.ConsoleWriterService.printStringsWithIndex;

class ConsoleWriterServiceTest {

    private final ConsoleWriterService consoleWriterService = new ConsoleWriterService();

    @Test
    void printStringsWithIndexTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<String> strings = new ArrayList<>();
        strings.add("Hello");
        strings.add("World");
        strings.add("!");

        printStringsWithIndex(strings);

        String expectedOutput = "1. Hello\n2. World\n3. !\n";
//        assertEquals(expectedOutput, outContent.toString());
    }
}