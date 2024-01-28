package org.du6ak.services;

import org.du6ak.models.User;
import org.du6ak.services.exceptions.WrongOperationException;

import java.util.List;

import static org.du6ak.services.AdminService.*;
import static org.du6ak.services.MeterReadingService.*;
import static org.du6ak.services.UserService.*;
import static org.du6ak.services.in.ConsoleReaderService.readString;
import static org.du6ak.services.out.ConsoleWriterService.*;
import static org.du6ak.services.out.ConsoleWriterService.printReadings;
import static org.du6ak.services.out.menu.AdminMenu.*;
import static org.du6ak.services.out.menu.MainMenu.showMainMenu;
import static org.du6ak.services.out.menu.UserMenu.*;

/**
 * This class provides the main menu and user menu for the application.
 */
public class MenuService {
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
    public static void mainMenuChoice() throws Exception {
        try {
            switch (showMainMenu()) {
                case 1 -> { // "1. Register"
                    printStrings("Введите имя пользователя:");
                    String username = readString();
                    printStrings("Введите пароль:");
                    String password = readString();
                    registration(username, password);
                    printStrings("Регистрация прошла успешно!");
                }
                case 2 -> { // "2. Login"
                    printStrings("Введите имя пользователя:");
                    String username = readString();
                    printStrings("Введите пароль:");
                    String password = readString();
                    login(username, password);
                    printStrings("Авторизация прошла успешно!");
                }
                case 3 -> { // "3. Exit"
                    if (showExitConfirm() == 1) {
                        printStrings("Завершение программы...");
                        exit();
                    }
                }
                default -> throw new WrongOperationException();
            }
        } catch (Exception e) {
            printErrors(e.getMessage());
        }
        if (getCurrentUser() != null) {
            if (getCurrentUser().isAdmin()) {
                adminMenuChoice(getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(); // User menu
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
    public static void userMenuChoice() throws Exception {
        try {
            switch (showUserMenu()) {
                case 1 -> { // "1. Submit meter reading"
                    sendReadings(getCurrentUser());
                    printStrings("Показание со счетчика успешно отправлено!");
                }
                case 2 -> { // "2. View current readings"
                    var latestReading = getActualReadings(getCurrentUser());
                    printStrings("Ваши актуальные показания (" + latestReading.getType() + "):\n");
                    printReadings(List.of(latestReading));
                }
                case 3 -> { // "3. View readings by month"
                    var readingByMonth = getMonthReadings(getCurrentUser());
                    printStrings("Ваши показания за " + (readingByMonth.get(0).getMonth() - 1));
                    printReadings(readingByMonth);
                }
                case 4 -> { // "4. View reading history"
                    var readings = getHistoryReadings(getCurrentUser());
                    printStrings("Ваша история подачи показаний:");
                    printReadings(readings);
                }
                case 5 -> { // "5. Change user"
                    if (showLogoutConfirm() == 1) {
                        logout();
                    }
                }
                case 6 -> { // "6. Exit"
                    if (showExitConfirm() == 1) {
                        printStrings("Завершение программы...");
                        exit();
                    }
                }
                default -> throw new WrongOperationException();
            }
        } catch (Exception e) {
            printErrors(e.getMessage());
        }
        if (getCurrentUser() == null) {
            mainMenuChoice(); // Main menu
        } else {
            if (isAdmin(getCurrentUser())) {
                adminMenuChoice(getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(); // User menu
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
    public static void adminMenuChoice(User currentUser) throws Exception {
        try {
            switch (showAdminMenu()) {
                case 1 -> sendReadings(currentUser); // "1. Submit meter reading"
                case 2 -> { // "2. View all current readings"
                    int result = showReadingsChoice();
                    if (result == 1) {
                        var actualReadings = getActualReadings(getCurrentUser());
                        printStrings("Ваши актуальные показания (" + actualReadings.getType() + "):\n");
                        printReadings(List.of(actualReadings));
                    } else if (result == 2) {
                        var actualReadings = getActualReadings(isRegistered(getTargetUsername()));
                        printStrings("Актуальные показания пользователя (" + actualReadings.getType() + "):\n");
                        printReadings(List.of(actualReadings));
                    }
                }
                case 3 -> { // "3. View readings by month"
                    int result = showReadingsChoice();
                    if (result == 1) {
                        var readingByMonth = getMonthReadings(getCurrentUser());
                        printStrings("Ваши показания за " + (readingByMonth.get(0).getMonth() - 1));
                        printReadings(readingByMonth);
                    } else if (result == 2) {
                        var readingByMonth = getMonthReadings(isRegistered(getTargetUsername()));
                        printStrings("Показания пользователя за " + (readingByMonth.get(0).getMonth() - 1));
                        printReadings(readingByMonth);
                    }
                }
                case 4 -> { // "4. View reading history"
                    int result = showReadingsChoice();
                    if (result == 1) {
                        var readings = getHistoryReadings(getCurrentUser());
                        printStrings("Ваша история подачи показаний:");
                        printReadings(readings);
                    } else if (result == 2) {
                        var readings = getHistoryReadings(isRegistered(getTargetUsername()));
                        printStrings("История подачи показаний пользователя:");
                        printReadings(readings);
                    }
                }
                case 5 -> { // "5. View user logs"
                    var logs = getUserLog(isRegistered(getTargetUsername()));
                    printStrings("Лог пользователя:");
                    printLogs(logs);
                }
                case 6 -> { // "6. Extend meter types"
                    int result = showReadingsEdit();
                    if (result == 1) {
                        printStrings("Введите новый тип счетчика:");
                        addReadingType(readString());
                    } else if (result == 2) {
                        printStrings("Введите тип счетчика для удаления:");
                        deleteReadingType(getTypeOfReading());
                    }
                }
                case 7 -> { // "7. Change user"
                    if (showLogoutConfirm() == 1) {
                        logout();
                    }
                }
                case 8 -> { // "8. Exit"
                    if (showExitConfirm() == 1) {
                        printStrings("Завершение программы...");
                        exit();
                    }
                }
                default -> throw new WrongOperationException();
            }
        } catch (Exception e) {
            printErrors(e.getMessage());
        }
        if (getCurrentUser() == null) {
            mainMenuChoice(); // Main menu
        } else {
            if (isAdmin(getCurrentUser())) {
                adminMenuChoice(getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(); // User menu
            }
        }
    }
}
