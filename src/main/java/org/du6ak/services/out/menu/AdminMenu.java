package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.IncorrectDataException;
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
     * Shows the admin menu to the user and returns the selected option.
     *
     * @return the selected option
     * @throws IncorrectDataException if the user enters an invalid option
     */
    public int showAdminMenu() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВыберите действие (введите номер операции от 1 до 8):",
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
     * Shows the choice for reading options to the user and returns the selected option.
     *
     * @return the selected option
     * @throws IncorrectDataException if the user enters an invalid option
     */
    public int showReadingsChoice() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВы хотите посмотреть свои показания?",
                "1. Мои показания",
                "2. Показания пользователя"
        );
        int value = consoleReaderService.readInt();
        if (value != 1 && value != 2) {
            throw new IncorrectDataException();
        }
        return value;
    }

    /**
     * Shows the editing options for reading types to the user and returns the selected option.
     *
     * @return the selected option
     * @throws IncorrectDataException if the user enters an invalid option
     */
    public int showReadingsEdit() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nРедактирование перечня подаваемых показаний",
                "1. Добавить новый счетчик",
                "2. Удалить счетчик"
        );
        int value = consoleReaderService.readInt();
        if (value != 1 && value != 2) {
            throw new IncorrectDataException();
        }
        return value;
    }

    /**
     * Prompts the user to enter a new reading type and returns the input.
     *
     * @return the new reading type
     */
    public String newReadingType() {
        consoleWriterService.printStrings("Введите новый тип счетчика:");
        return consoleReaderService.readString();
    }


}
