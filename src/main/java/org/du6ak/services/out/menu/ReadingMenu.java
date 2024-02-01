package org.du6ak.services.out.menu;

import org.du6ak.repositories.ReadingTypes;
import org.du6ak.services.exceptions.IncorrectDataException;
import org.du6ak.services.exceptions.WrongOperationException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;

import java.time.Month;
import java.util.Arrays;
import java.util.List;


/**
 * This class provides methods for adding meter readings.
 */
public class ReadingMenu {
    private static final ReadingMenu instance = new ReadingMenu();

    public static ReadingMenu getInstance() {
        return instance;
    }

    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();
    private final ReadingTypes readingTypes = ReadingTypes.getInstance();

    /**
     * This method is used to show the menu for adding meter readings.
     *
     * @return the index of the selected option
     * @throws WrongOperationException if there is an error while reading input
     */
    public String showReadingTypes() throws WrongOperationException, IncorrectDataException {
        consoleWriterService.printStrings(
                "Выберите нужный тип счётчика (введите номер операции):");
        consoleWriterService.printStringsWithIndex(readingTypes.getReadingTypes());
        int index = consoleReaderService.readInt();
        if (index < 1 || index > readingTypes.getReadingTypes().size()) {
            throw new IncorrectDataException();
        }
        return readingTypes.getReadingTypes().get(index - 1);
    }

    public Long getContractNumber() throws WrongOperationException {
        consoleWriterService.printStrings("Введите номер договора:");
        return consoleReaderService.readLong();
    }

    public int getValue() throws WrongOperationException {
        consoleWriterService.printStrings(
                "Введите показания счётчика:",
                "(Показания вводится без первых нулей и без цифр после запятой)");
        return consoleReaderService.readInt();
    }

    public Month getMonth() throws WrongOperationException, IncorrectDataException {
        consoleWriterService.printStrings(
                "Введите номер месяца, за который хотите внести/посмотреть показания");
        List<String> months = List.of(Arrays.toString(Month.values()));
        consoleWriterService.printStringsWithIndex(months);
        return Month.values()[consoleReaderService.readInt() - 1];
    }

}
