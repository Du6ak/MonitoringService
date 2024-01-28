package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.in.ConsoleReaderService.readInt;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;

public class UserMenu {

    public static int showUserMenu() throws WrongOperationException {
        printStrings(
                "Выберите действие (введите номер операции от 1 до 6):",
                "1. Внести показания",
                "2. Посмотреть актуальные(последние) показания",
                "3. Посмотреть показания за конкретный месяц",
                "4. Посмотреть свою историю подачи показаний",
                "5. Сменить пользователя",
                "6. Выход из программы"
        );
        return readInt();
    }

    public static int showLogoutConfirm() throws WrongOperationException {
        printStrings(
                "Вы действительно хотите сменить пользователя?",
                "1. Да",
                "2. Нет"
        );
        int value = readInt();
        if (value != 1 && value != 2) {
            throw new WrongOperationException();
        }
        return value;
    }

    public static int showExitConfirm() throws WrongOperationException {
        printStrings(
                "Вы действительно хотите закрыть программу?",
                "1. Да",
                "2. Нет"
        );
        int value = readInt();
        if (value != 1 && value != 2) {
            throw new WrongOperationException();
        }
        return value;
    }
}
