package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.MeterReadingService.getReadingTypes;
import static org.du6ak.services.in.ConsoleReaderService.*;
import static org.du6ak.services.out.ConsoleWriterService.*;

public class ReadingMenu{

    public static int showReadingMenu() throws WrongOperationException {
        printStrings(
                "Выберите нужный тип счётчика (введите номер операции):");
        printStringsWithIndex(getReadingTypes());
        return readInt();
    }
    public static Long getContractNumber() throws WrongOperationException {
        printStrings("Введите номер договора:");
        return readLong();
    }
    public static int getValue() throws WrongOperationException {
        printStrings(
                "Введите показания счётчика:",
                "(Показания вводится без первых нулей и без цифр после запятой)");
        return readInt();
    }
    public static int getMonth() throws WrongOperationException {
        printStrings(
                "Введите номер месяца, за который хотите внести показания",
                "(Если вносите за январь, введите 1 и т.д.)");
        return readInt();
    }

}
