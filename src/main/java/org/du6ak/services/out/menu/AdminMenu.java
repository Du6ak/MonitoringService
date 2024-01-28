package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.in.ConsoleReaderService.readInt;
import static org.du6ak.services.in.ConsoleReaderService.readString;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;

/**
 * Provides methods for interacting with the user through the console.
 */
public class AdminMenu {

    /**
     * Displays the main menu for the administrator.
     *
     * @return the user's choice as an integer
     * @throws WrongOperationException if the user enters an invalid choice
     */
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

    /**
     * Asks the user whether they want to view their own readings or another user's readings.
     *
     * @return the user's choice as an integer
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public static int showReadingsChoice() throws WrongOperationException {
        printStrings(
                "Вы хотите посмотреть свои показания?",
                "1. Мои показания",
                "2. Показания пользователя"
        );
        return readInt();
    }

    /**
     * Asks the user whether they want to edit the list of supported readings.
     *
     * @return the user's choice as an integer
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public static int showReadingsEdit() throws WrongOperationException {
        printStrings(
                "Редактирование перечня подаваемых показаний",
                "1. Добавить новый тип счетчика",
                "2. Удалить тип счетчика"
        );
        return readInt();
    }

    /**
     * Prompts the user to enter a username and returns it.
     *
     * @return the username entered by the user
     */
    public static String getTargetUsername() {
        printStrings("Введите имя пользователя:");
        return readString();
    }
}
