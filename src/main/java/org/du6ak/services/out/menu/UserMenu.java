package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.in.ConsoleReaderService.readInt;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;

/**
 * Provides methods for interacting with the user through the console.
 */
public class UserMenu {

    /**
     * Displays the user menu and returns the user's selection.
     *
     * @return the user's selection
     * @throws WrongOperationException if the user enters an invalid option
     */
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

    /**
     * Displays the logout confirmation prompt and returns the user's selection.
     *
     * @return the user's selection
     * @throws WrongOperationException if the user enters an invalid option
     */
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

    /**
     * Displays the exit confirmation prompt and returns the user's selection.
     *
     * @return the user's selection
     * @throws WrongOperationException if the user enters an invalid option
     */
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
