package org.du6ak.services.out.menu;

import org.du6ak.models.Role;
import org.du6ak.services.exceptions.IncorrectDataException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;

import java.sql.SQLException;

/**
 * This class provides a menu for the user to interact with the system.
 * It contains methods for displaying menus, prompting for input, and validating user input.
 */
public class UserMenu {

    /**
     * The single instance of the UserMenu class.
     */
    private static final UserMenu instance = new UserMenu();

    /**
     * Returns the single instance of the UserMenu class.
     */
    public static UserMenu getInstance() {
        return instance;
    }

    /**
     * The ConsoleReaderService for reading user input.
     */
    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();

    /**
     * The ConsoleWriterService for writing output to the console.
     */
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();

    /**
     * The Role class for managing user roles.
     */
    private final Role role = Role.getInstance();

    /**
     * Displays the user menu and prompts the user to select an option.
     *
     * @return the user's selection
     * @throws IncorrectDataException if the user enters an invalid option
     */
    public int showUserMenu() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВыберите действие (введите номер операции от 1 до 6):",
                "1. Внести показания",
                "2. Посмотреть актуальные(последние) показания",
                "3. Посмотреть показания за конкретный месяц",
                "4. Посмотреть свою историю подачи показаний",
                "5. Сменить пользователя",
                "6. Выход из программы"
        );
        int value = consoleReaderService.readInt();
        if (value < 1 || value > 6) {
            throw new IncorrectDataException();
        }
        return value;
    }

    /**
     * Displays a confirmation message for logging out of the system, and prompts the user to confirm.
     *
     * @return 1 if the user confirms, 2 if they cancel
     * @throws IncorrectDataException if the user enters an invalid option
     */
    public int showLogoutConfirm() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВы действительно хотите сменить пользователя?",
                "1. Да",
                "2. Нет"
        );
        int value = consoleReaderService.readInt();
        if (value != 1 && value != 2) {
            throw new IncorrectDataException();
        }
        return value;
    }

    /**
     * Displays a confirmation message for exiting the system, and prompts the user to confirm.
     *
     * @return 1 if the user confirms, 2 if they cancel
     * @throws IncorrectDataException if the user enters an invalid option
     */
    public int showExitConfirm() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВы действительно хотите закрыть программу?",
                "1. Да",
                "2. Нет"
        );
        int value = consoleReaderService.readInt();
        if (value != 1 && value != 2) {
            throw new IncorrectDataException();
        }
        return value;
    }

    /**
     * Prompts the user to enter their username.
     *
     * @return the username
     */
    public String getUsernameMenu() {
        consoleWriterService.printStrings("Введите имя пользователя:");
        return consoleReaderService.readString();
    }

    /**
     * Prompts the user to enter their password.
     *
     * @return the password
     */
    public String getPasswordMenu() {
        consoleWriterService.printStrings("Введите пароль:");
        return consoleReaderService.readString();
    }

    /**
     * Prompts the user to select a role from a list of available roles.
     *
     * @return the role ID
     * @throws SQLException           if there is an error loading the role data
     * @throws IncorrectDataException if the user enters an invalid input
     */
    public int getRoleMenu() throws SQLException, IncorrectDataException {
        consoleWriterService.printStrings("Выберите роль:");
        consoleWriterService.printRolesWithId();
        int roleId = consoleReaderService.readInt();
        if (role.isContains(roleId)) {
            return roleId;
        }
        throw new IncorrectDataException();
    }
}
