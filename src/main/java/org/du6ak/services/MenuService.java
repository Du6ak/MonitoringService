package org.du6ak.services;

import org.du6ak.models.*;
import org.du6ak.services.exceptions.IncorrectDataException;
import org.du6ak.services.out.ConsoleWriterService;
import org.du6ak.services.out.menu.AdminMenu;
import org.du6ak.services.out.menu.MainMenu;
import org.du6ak.services.out.menu.ReadingMenu;
import org.du6ak.services.out.menu.UserMenu;


/**
 * This class provides the main menu and user menu for the application.
 */
public class MenuService {
    private static final MenuService INSTANCE = new MenuService();

    public static MenuService getInstance() {
        return INSTANCE;
    }

    private final UserService userService = UserService.getInstance();
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();
    private final UserMenu userMenu = UserMenu.getInstance();
    private final AdminMenu adminMenu = AdminMenu.getInstance();
    private final MeterReadingService meterReadingService = MeterReadingService.getInstance();
    private final ReadingMenu readingMenu = ReadingMenu.getInstance();
    private final User user = User.getInstance();
    private final ReadingType readingType = ReadingType.getInstance();
    private final Log log = Log.getInstance();
    private final Role role = Role.getInstance();

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
                    String username = userMenu.getUsernameMenu();
                    String password = userMenu.getPasswordMenu();
                    int roleId = userMenu.getRoleMenu();
                    userService.registration(username, password, roleId);
                    consoleWriterService.printStrings("Регистрация прошла успешно!");

                }
                case 2 -> { // "2. Login"
                    String username = userMenu.getUsernameMenu();
                    String password = userMenu.getPasswordMenu();
                    userService.login(username, password);
                    consoleWriterService.printStrings("Авторизация прошла успешно!");
                }
                case 3 -> { // "3. Exit"
                    if (userMenu.showExitConfirm() == 1) {
                        consoleWriterService.printStrings("Завершение программы...");
                        userService.exit();
                    }
                }
                default -> throw new IncorrectDataException();
            }
        } catch (Exception e) {
            consoleWriterService.printErrors(e.getMessage());
        }
        if (userService.getCurrentUser() != null) {
            if ((role.getRoleName(user.getUserRole(userService.getCurrentUser()))).equalsIgnoreCase("admin")) {
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
     * @param currentUser the currently authenticated user
     * @throws Exception if there is an error while processing the user input
     */
    public void userMenuChoice(String currentUser) throws Exception {
        try {
            switch (userMenu.showUserMenu()) {
                case 1 -> { // "1. Submit meter reading"
                    int typeId = readingMenu.showReadingTypes();
                    int contractNumber = readingMenu.getContractNumber();
                    int value = readingMenu.getValue();
                    int month = readingMenu.getMonth();
                    meterReadingService.sendReadings(currentUser, typeId, contractNumber, value, month);
                    consoleWriterService.printStrings("Показание со счетчика успешно отправлено!");
                }
                case 2 -> { // "2. View current readings"
                    int typeId = readingMenu.showReadingTypes();
                    consoleWriterService.printStrings(meterReadingService.getActualReadings(currentUser, typeId));
                }
                case 3 -> { // "3. View readings by month"
                    int typeId = readingMenu.showReadingTypes();
                    int month = readingMenu.getMonth();
                    consoleWriterService.printList(meterReadingService.getMonthReadings(currentUser, typeId, month));
                }
                case 4 -> { // "4. View reading history"
                    consoleWriterService.printStrings("Ваша история подачи показаний:");
                    consoleWriterService.printList(meterReadingService.getHistoryReadings(currentUser));
                }
                case 5 -> { // "5. Change user"
                    if (userMenu.showLogoutConfirm() == 1) {
                        userService.logout(currentUser);
                    }
                }
                case 6 -> { // "6. Exit"
                    if (userMenu.showExitConfirm() == 1) {
                        consoleWriterService.printStrings("Завершение программы...");
                        userService.exit();
                    }
                }
                default -> throw new IncorrectDataException();
            }
        } catch (Exception e) {
            consoleWriterService.printErrors(e.getMessage());
        }
        if (userService.getCurrentUser() != null) {
            if ((role.getRoleName(user.getUserRole(userService.getCurrentUser()))).equalsIgnoreCase("admin")) {
                adminMenuChoice(userService.getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(userService.getCurrentUser()); // User menu
            }
        } else {
            mainMenuChoice(); // Main menu
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
    public void adminMenuChoice(String currentUser) throws Exception {
        try {
            switch (adminMenu.showAdminMenu()) {
                case 1 -> {
                    int typeId = readingMenu.showReadingTypes();
                    int contractNumber = readingMenu.getContractNumber();
                    int value = readingMenu.getValue();
                    int month = readingMenu.getMonth();
                    meterReadingService.sendReadings(currentUser, typeId, contractNumber, value, month);
                    consoleWriterService.printStrings("Показание со счетчика успешно отправлено!\n");
                }
                case 2 -> { // "2. View all current readings"
                    int result = adminMenu.showReadingsChoice();
                    if (result == 1) {
                        int typeId = readingMenu.showReadingTypes();
                        consoleWriterService.printStrings(meterReadingService.getActualReadings(currentUser, typeId));
                    } else if (result == 2) {
                        String targetUsername = userMenu.getUsernameMenu();
                        int typeId = readingMenu.showReadingTypes();
                        consoleWriterService.printStrings(meterReadingService.getActualReadings(targetUsername, typeId));
                    }
                }
                case 3 -> { // "3. View readings by month"
                    int result = adminMenu.showReadingsChoice();
                    if (result == 1) {
                        int typeId = readingMenu.showReadingTypes();
                        int month = readingMenu.getMonth();
                        consoleWriterService.printList(meterReadingService.getMonthReadings(currentUser, typeId, month));
                    } else if (result == 2) {
                        String targetUsername = userMenu.getUsernameMenu();
                        int typeId = readingMenu.showReadingTypes();
                        int month = readingMenu.getMonth();
                        consoleWriterService.printList(meterReadingService.getMonthReadings(targetUsername, typeId, month));
                    }
                }
                case 4 -> { // "4. View reading history"
                    int result = adminMenu.showReadingsChoice();
                    if (result == 1) {
                        consoleWriterService.printStrings("Ваша история подачи показаний:");
                        consoleWriterService.printList(meterReadingService.getHistoryReadings(currentUser));
                    } else if (result == 2) {
                        String targetUsername = userMenu.getUsernameMenu();
                        consoleWriterService.printStrings("История подачи показаний " + targetUsername + ":");
                        consoleWriterService.printList(meterReadingService.getHistoryReadings(targetUsername));
                    }
                }
                case 5 -> { // "5. View user logs"
                    String targetUsername = userMenu.getUsernameMenu();
                    consoleWriterService.printStrings("\nЛог пользователя " + targetUsername + ":");
                    consoleWriterService.printList(log.getLogs(targetUsername, user.getUserId(targetUsername)));
                }
                case 6 -> { // "6. Extend meter types"
                    int result = adminMenu.showReadingsEdit();
                    if (result == 1) {
                        String newReadingType = adminMenu.newReadingType();
                        readingType.addReadingType(newReadingType);
                        consoleWriterService.printStrings("Тип счетчика " + newReadingType + " добавлен!");
                    } else if (result == 2) {
                        int typeId = readingMenu.showReadingTypes();
                        String typeName = readingType.getTypeName(typeId);
                        readingType.deleteReadingType(typeId);
                        consoleWriterService.printStrings("Тип счетчика " + typeName + " удален!");
                    }
                }
                case 7 -> { // "7. Change user"
                    if (userMenu.showLogoutConfirm() == 1) {
                        userService.logout(currentUser);
                    }
                }
                case 8 -> { // "8. Exit"
                    if (userMenu.showExitConfirm() == 1) {
                        consoleWriterService.printStrings("Завершение программы...");
                        userService.exit();
                    }
                }
                default -> throw new IncorrectDataException();
            }
        } catch (Exception e) {
            consoleWriterService.printErrors(e.getMessage());
        }
        if (userService.getCurrentUser() != null) {
            if ((role.getRoleName(user.getUserRole(userService.getCurrentUser()))).equalsIgnoreCase("admin")) {
                adminMenuChoice(userService.getCurrentUser()); // Admin menu
            } else {
                userMenuChoice(userService.getCurrentUser()); // User menu
            }
        } else {
            mainMenuChoice(); // Main menu
        }
    }
}
