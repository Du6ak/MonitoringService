package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.in.ConsoleReaderService.readInt;
import static org.du6ak.services.in.ConsoleReaderService.readString;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;

public class AdminMenu {
    public static int showAdminMenu() throws WrongOperationException {
        printStrings(
                "Выберите действие (введите номер операции от 1 до 8):",
                "1. Внести показания",
                "2. Посмотреть актуальные(последние) показания",
                "3. Посмотреть показания за конкретный месяц",
                "4. Посмотреть историю подачи показаний",
                "5. Посмотреть лог пользователя",
                "6. Расширить перечень показаний",
                "7. Сменить пользователя",
                "8. Выход из программы"
        );
        return readInt();
    }

    public static int showReadingsChoice() throws WrongOperationException {
        printStrings(
                "Вы хотите посмотреть свои показания?",
                "1. Мои показания",
                "2. Показания пользователя"
        );
        return readInt();
    }
    public static int showReadingsEdit() throws WrongOperationException {
        printStrings(
                "Редактирование перечня подаваемых показаний",
                "1. Добавить новый тип счетчика",
                "2. Удалить тип счетчика"
        );
        return readInt();
    }

    public static String getTargetUsername() {
        printStrings("Введите имя пользователя:");
        return readString();
    }
}
