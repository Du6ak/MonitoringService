package org.du6ak.services.out.menu;

import org.du6ak.models.ReadingType;
import org.du6ak.services.exceptions.IncorrectDataException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;

import java.sql.SQLException;

/**
 * This class provides methods for interacting with the user through the console.
 * It is used to prompt the user for input and display information on the console.
 */
public class ReadingMenu {

    /**
     * A static instance of this class.
     */
    private static final ReadingMenu instance = new ReadingMenu();

    /**
     * Returns the single instance of this class.
     *
     * @return the instance
     */
    public static ReadingMenu getInstance() {
        return instance;
    }

    /**
     * The console reader service.
     */
    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();

    /**
     * The console writer service.
     */
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();

    /**
     * The reading type service.
     */
    private final ReadingType readingType = ReadingType.getInstance();

    /**
     * This method is used to show the menu for adding meter readings.
     *
     * @return the index of the selected option
     */
    public int showReadingTypes() throws IncorrectDataException, SQLException {
        consoleWriterService.printStrings(
                "\nВыберите нужный тип счётчика:");
        consoleWriterService.printTypesWithId();
        int typeId = consoleReaderService.readInt();
        if (readingType.isContains(typeId)) {
            return typeId;
        }
        throw new IncorrectDataException();
    }

    /**
     * Gets the contract number.
     *
     * @return the contract number
     * @throws IncorrectDataException if the input is incorrect
     */
    public int getContractNumber() throws IncorrectDataException {
        consoleWriterService.printStrings("Введите номер договора:");
        return consoleReaderService.readInt();
    }

    /**
     * Gets the value.
     *
     * @return the value
     * @throws IncorrectDataException if the input is incorrect
     */
    public int getValue() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВведите показания счётчика:",
                "(Значение показания вводится без первых нулей и без цифр после запятой)");
        return consoleReaderService.readInt();
    }

    /**
     * Gets the month.
     *
     * @return the month
     * @throws IncorrectDataException if the input is incorrect
     */
    public int getMonth() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВведите номер месяца, за который хотите внести/посмотреть показания");
        consoleWriterService.printMonthsWithId();
        int monthNumber = consoleReaderService.readInt();
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IncorrectDataException();
        }
        return monthNumber;
    }

}
