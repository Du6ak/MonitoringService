package org.du6ak.services;

import org.du6ak.models.User;
import org.du6ak.repositories.Logs;
import org.du6ak.repositories.ReadingTypes;
import org.du6ak.repositories.Roles;
import org.du6ak.services.exceptions.WrongOperationException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;
import org.du6ak.services.out.menu.AdminMenu;
import org.du6ak.services.out.menu.MainMenu;
import org.du6ak.services.out.menu.ReadingMenu;
import org.du6ak.services.out.menu.UserMenu;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import static org.du6ak.services.MeterReadingService.*;

import static org.du6ak.services.out.menu.AdminMenu.*;


/**
 * This class provides the main menu and user menu for the application.
 */
public class MenuService {
    private static final MenuService INSTANCE = new MenuService();

    public static MenuService getInstance() {
        return INSTANCE;
    }

    private final UserService userService = UserService.getInstance();
    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();
    private final MainMenu mainMenu = MainMenu.getInstance();
    private final UserMenu userMenu = UserMenu.getInstance();
    private final AdminMenu adminMenu = AdminMenu.getInstance();
    private final MeterReadingService meterReadingService = MeterReadingService.getInstance();
    private final ReadingMenu readingMenu = ReadingMenu.getInstance();
    private final Logs logs = Logs.getInstance();
    private final ReadingTypes readingTypes = ReadingTypes.getInstance();

    /**
     * This method displays the main menu and prompts the user to make a selection. The user can then choose
     * from options such as registering, logging in, or exiting the program. If the user chooses to exit, the
     * program will terminate. If the user chooses to register, they will be prompted to enter their username
     * and password. If the user chooses to log in, they will be prompted to enter their username and password.
     * If the user is successfully authenticated, they will be taken to the main menu. If the user enters an
     * invalid option or provides invalid input, they will be prompted to try again.
     *
     * @throws Exception if there is an error while processing the user input
     */
    public void mainMenuChoice() throws Exception {
        try {
            switch (MainMenu.getInstance().showMainMenu()) {
                case 1 -> { // "1. Register"
                    consoleWriterService.printStrings("Введите имя пользователя:");
                    String username = consoleReaderService.readString();
                    consoleWriterService.printStrings("Введите пароль:");
                    String password = consoleReaderService.readString();
                    consoleWriterService.printStrings("Выберите роль:");
                    Roles role = consoleWriterService.printRolesWithIndex(new ArrayList<>(Arrays.asList(Roles.values())));
                    userService.registration(username, password, role);
                    consoleWriterService.printStrings("Регистрация прошла успешно!");
                }
                case 2 -> { // "2. Login"
                    consoleWriterService.printStrings("Введите имя пользователя:");
                    String username = consoleReaderService.readString();
                    consoleWriterService.printStrings("Введите пароль:");
                    String password = consoleReaderService.readString();
                    userService.login(username, password);
                    consoleWriterService.printStrings("Авторизация прошла успешно!");
                }
                case 3 -> { // "3. Exit"
                    if (userMenu.showExitConfirm() == 1) {
                        consoleWriterService.printStrings("Завершение программы...");
                        userService.exit();
                    }
                }
                default -> throw new WrongOperationException();
            }
        } catch (Exception e) {
            consoleWriterService.printErrors(e.getMessage());
        }
        if (userService.getCurrentUser() != null) {
            if (userService.getCurrentUser().getRole() == Roles.ADMIN) {
                adminMenuChoice(userService.getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(userService.getCurrentUser()); // User menu
            }
        } else {
            mainMenuChoice(); // Main menu
        }
    }

    /**
     * This method displays the user menu and prompts the user to make a selection. The user can then choose
     * from options such as submitting meter readings, viewing their current and historical readings,
     * changing their user account, or exiting the program. If the user chooses to exit, the program will
     * terminate. If the user chooses to submit meter readings, they will be prompted to enter the reading
     * value and type. If the user chooses to view their current readings, they will be shown their current
     * readings for the selected meter. If the user chooses to view their historical readings, they will be
     * shown their historical readings for the selected meter. If the user chooses to change their user
     * account, they will be prompted to enter a new username and password. If the user is successfully
     * authenticated, they will be taken to the main menu. If the user enters an invalid option or provides
     * invalid input, they will be prompted to try again.
     *
     * @throws Exception if there is an error while processing the user input
     */
    public void userMenuChoice(User currentUser) throws Exception {
        try {
            switch (userMenu.showUserMenu()) {
                case 1 -> { // "1. Submit meter reading"
                    String type = readingMenu.showReadingTypes(); // get the meter reading type
                    Long contractNumber = readingMenu.getContractNumber(); // get the contract number
                    int value = readingMenu.getValue(); // get the reading value
                    Month month = readingMenu.getMonth(); // get the month of the reading
                    meterReadingService.sendReadings(currentUser, type, contractNumber, value, month);
                    consoleWriterService.printStrings("Показание со счетчика успешно отправлено!");
                }
                case 2 -> { // "2. View current readings"
                    String type = readingMenu.showReadingTypes(); // get the meter reading type
                    var latestReading = meterReadingService.getActualReadings(currentUser, type);
                    consoleWriterService.printStrings("Ваши актуальные показания (" + latestReading.getType() + "):\n");
                    consoleWriterService.printReadings(List.of(latestReading));
                }
                case 3 -> { // "3. View readings by month"
                    String type = readingMenu.showReadingTypes();
                    Month month = readingMenu.getMonth();
                    consoleWriterService.printStrings("Ваши показания за " + month);
                    consoleWriterService.printReadings(meterReadingService.getMonthReadings(currentUser, type, month));
                }
                case 4 -> { // "4. View reading history"
                    var readings = meterReadingService.getHistoryReadings(currentUser);
                    consoleWriterService.printStrings("Ваша история подачи показаний:");
                    consoleWriterService.printReadings(readings);
                }
                case 5 -> { // "5. Change user"
                    if (userMenu.showLogoutConfirm() == 1) {
                        userService.logout();
                    }
                }
                case 6 -> { // "6. Exit"
                    if (userMenu.showExitConfirm() == 1) {
                        consoleWriterService.printStrings("Завершение программы...");
                        userService.exit();
                    }
                }
                default -> throw new WrongOperationException();
            }
        } catch (Exception e) {
            consoleWriterService.printErrors(e.getMessage());
        }
        if (userService.getCurrentUser() == null) {
            mainMenuChoice(); // Main menu
        } else {
            if (userService.getCurrentUser().getRole() == Roles.ADMIN) {
                adminMenuChoice(userService.getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(userService.getCurrentUser()); // User menu
            }
        }
    }

    /**
     * This method displays the admin menu and prompts the user to make a selection. The user can then choose
     * from options such as submitting meter readings, viewing all current and historical readings,
     * viewing a specific user's readings, viewing the system logs, extending the list of meter types, or
     * exiting the program. If the user chooses to exit, the program will terminate. If the user chooses to
     * submit meter readings, they will be prompted to enter the reading value and type. If the user chooses
     * to view all current readings, they will be shown all current readings for all registered users. If
     * the user chooses to view a specific user's readings, they will be prompted to enter the username and
     * be shown that user's current and historical readings. If the user chooses to view the system logs,
     * they will be shown the system logs for all registered users. If the user chooses to extend the list of
     * meter types, they will be prompted to enter a new meter type. If the user chooses to edit the list of
     * meter types, they will be prompted to enter a meter type and be prompted to confirm the deletion. If
     * the user is successfully authenticated, they will be taken to the main menu. If the user enters an
     * invalid option or provides invalid input, they will be prompted to try again.
     *
     * @param currentUser the currently authenticated user
     * @throws Exception if there is an error while processing the user input
     */
    public void adminMenuChoice(User currentUser) throws Exception {
        try {
            switch (adminMenu.showAdminMenu()) {
                case 1 -> {
                    String type = readingMenu.showReadingTypes(); // get the meter reading type
                    Long contractNumber = readingMenu.getContractNumber(); // get the contract number
                    int value = readingMenu.getValue(); // get the reading value
                    Month month = readingMenu.getMonth(); // get the month of the reading
                    meterReadingService.sendReadings(currentUser, type, contractNumber, value, month);
                    consoleWriterService.printStrings("Показание со счетчика успешно отправлено!");
                }
                case 2 -> { // "2. View all current readings"
                    int result = adminMenu.showReadingsChoice();
                    if (result == 1) {
                        String type = readingMenu.showReadingTypes(); // get the meter reading type
                        var latestReading = meterReadingService.getActualReadings(currentUser, type);
                        consoleWriterService.printStrings("Ваши актуальные показания (" + latestReading.getType() + "):\n");
                        consoleWriterService.printReadings(List.of(latestReading));
                    } else if (result == 2) {
                        String type = readingMenu.showReadingTypes(); // get the meter reading type
                        User targetUser = userService.isRegistered(adminMenu.getTargetUsername());
                        var latestReading = meterReadingService.getActualReadings(targetUser, type);
                        consoleWriterService.printStrings("Ваши актуальные показания (" + latestReading.getType() + "):\n");
                        consoleWriterService.printReadings(List.of(latestReading));
                    }
                }
                case 3 -> { // "3. View readings by month"
                    int result = adminMenu.showReadingsChoice();
                    if (result == 1) {
                        String type = readingMenu.showReadingTypes();
                        Month month = readingMenu.getMonth();
                        consoleWriterService.printStrings("Ваши показания за " + month);
                        consoleWriterService.printReadings(meterReadingService.getMonthReadings(currentUser, type, month));
                    } else if (result == 2) {
                        String type = readingMenu.showReadingTypes();
                        Month month = readingMenu.getMonth();
                        User targetUser = userService.isRegistered(adminMenu.getTargetUsername());
                        consoleWriterService.printStrings("Ваши показания за " + month);
                        consoleWriterService.printReadings(meterReadingService.getMonthReadings(targetUser, type, month));
                    }
                }
                case 4 -> { // "4. View reading history"
                    int result = adminMenu.showReadingsChoice();
                    if (result == 1) {
                        var readings = meterReadingService.getHistoryReadings(currentUser);
                        consoleWriterService.printStrings("Ваша история подачи показаний:");
                        consoleWriterService.printReadings(readings);
                    } else if (result == 2) {
                        User targetUser = userService.isRegistered(adminMenu.getTargetUsername());
                        var readings = meterReadingService.getHistoryReadings(targetUser);
                        consoleWriterService.printStrings("Ваша история подачи показаний:");
                        consoleWriterService.printReadings(readings);
                    }
                }
                case 5 -> { // "5. View user logs"
                    User targetUser = userService.isRegistered(adminMenu.getTargetUsername());
                    var userLog = logs.getLogs(targetUser);
                    consoleWriterService.printStrings("Лог пользователя:");
                    consoleWriterService.printLogs(userLog);
                }
                case 6 -> { // "6. Extend meter types"
                    int result = adminMenu.showReadingsEdit();
                    if (result == 1) {
                        consoleWriterService.printStrings("Введите новый тип счетчика:");
                        readingTypes.addReadingType(consoleReaderService.readString());
                    } else if (result == 2) {
                        consoleWriterService.printStrings("Введите тип счетчика для удаления:");
                        String typeForDelete = readingMenu.showReadingTypes();
                        readingTypes.deleteReadingType(typeForDelete);
                    }
                }
                case 7 -> { // "7. Change user"
                    if (userMenu.showLogoutConfirm() == 1) {
                        userService.logout();
                    }
                }
                case 8 -> { // "8. Exit"
                    if (userMenu.showExitConfirm() == 1) {
                        consoleWriterService.printStrings("Завершение программы...");
                        userService.exit();
                    }
                }
                default -> throw new WrongOperationException();
            }
        } catch (Exception e) {
            consoleWriterService.printErrors(e.getMessage());
        }
        if (userService.getCurrentUser() == null) {
            mainMenuChoice(); // Main menu
        } else {
            if (userService.getCurrentUser().getRole() == Roles.ADMIN) {
                adminMenuChoice(userService.getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(userService.getCurrentUser()); // User menu
            }
        }
    }
}
