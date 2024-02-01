package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;

/**
 * Provides methods for interacting with the user through the console.
 */
public class AdminMenu {

    private static final AdminMenu INSTANCE = new AdminMenu();
    public static AdminMenu getInstance() {
        return INSTANCE;
    }
    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();

    /**
     * Displays the main menu for the administrator.
     *
     * @return the user's choice as an integer
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public int showAdminMenu() throws WrongOperationException {
        consoleWriterService.printStrings(
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
        return consoleReaderService.readInt();
    }

    /**
     * Asks the user whether they want to view their own readings or another user's readings.
     *
     * @return the user's choice as an integer
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public int showReadingsChoice() throws WrongOperationException {
        consoleWriterService.printStrings(
                "Вы хотите посмотреть свои показания?",
                "1. Мои показания",
                "2. Показания пользователя"
        );
        int value = consoleReaderService.readInt();
        if (value != 1 && value != 2) {
            throw new WrongOperationException();
        }
        return value;
    }

    /**
     * Asks the user whether they want to edit the list of supported readings.
     *
     * @return the user's choice as an integer
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public int showReadingsEdit() throws WrongOperationException {
        consoleWriterService.printStrings(
                "Редактирование перечня подаваемых показаний",
                "1. Добавить новый счетчик",
                "2. Удалить счетчик"
        );
        int value = consoleReaderService.readInt();
        if (value != 1 && value != 2) {
            throw new WrongOperationException();
        }
        return value;
    }

    /**
     * Prompts the user to enter a username and returns it.
     *
     * @return the username entered by the user
     */
    public String getTargetUsername() {
        consoleWriterService.printStrings("Введите имя пользователя:");
        return consoleReaderService.readString();
    }
}
