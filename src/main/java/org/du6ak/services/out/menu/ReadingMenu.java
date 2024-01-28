package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.MeterReadingService.getReadingTypes;
import static org.du6ak.services.in.ConsoleReaderService.*;
import static org.du6ak.services.out.ConsoleWriterService.*;

/**
 * This class provides methods for adding meter readings.
 */
public class ReadingMenu {

    /**
     * This method is used to show the menu for adding meter readings.
     *
     * @return the index of the selected option
     * @throws WrongOperationException if there is an error while reading input
     */
    public static int showReadingMenu() throws WrongOperationException {
        printStrings(
                "Выберите нужный тип счётчика (введите номер операции):");
        printStringsWithIndex(getReadingTypes());
        return readInt();
    }

    /**
     * This method is used to get the contract number.
     *
     * @return the contract number
     * @throws WrongOperationException if there is an error while reading input
     */
    public static Long getContractNumber() throws WrongOperationException {
        printStrings("Введите номер договора:");
        return readLong();
    }

    /**
     * This method is used to get the value.
     *
     * @return the value
     * @throws WrongOperationException if there is an error while reading input
     */
    public static int getValue() throws WrongOperationException {
        printStrings(
                "Введите показания счётчика:",
                "(Показания вводится без первых нулей и без цифр после запятой)");
        return readInt();
    }

    /**
     * This method is used to get the month.
     *
     * @return the month
     * @throws WrongOperationException if there is an error while reading input
     */
    public static int getMonth() throws WrongOperationException {
        printStrings(
                "Введите номер месяца, за который хотите внести показания",
                "(Если вносите за январь, введите 1 и т.д.)");
        return readInt();
    }

}
