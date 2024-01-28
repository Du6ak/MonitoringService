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

public class MenuService {
    // Главное меню
    public static void mainMenuChoice() throws Exception {
        try {
            switch (showMainMenu()) {
                case 1 -> {                                      //"1. Регистрация"
                    printStrings("Введите имя пользователя:");
                    String username = readString();
                    printStrings("Введите пароль:");
                    String password = readString();
                    registration(username, password);
                    printStrings("Регистрация прошла успешно!");
                }
                case 2 -> {                              //"2. Авторизация"
                    printStrings("Введите имя пользователя:");
                    String username = readString();
                    printStrings("Введите пароль:");
                    String password = readString();
                    login(username, password);
                    printStrings("Авторизация прошла успешно!");
                }
                case 3 -> {                             //"3. Выйти из программы"
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
                adminMenuChoice(getCurrentUser());   // Меню админа
            } else {
                userMenuChoice(); // Меню пользователя
            }
        } else {
            mainMenuChoice();  // Главное меню
        }
    }

    // Меню пользователя
    public static void userMenuChoice() throws Exception {
        try {
            switch (showUserMenu()) {
                case 1 -> {                                                 //"1. Внести показания"
                    sendReadings(getCurrentUser());
                    printStrings("Показание со счетчика успешно отправлено!");
                }
                case 2 -> {                                                 //"2. Посмотреть актуальные(последние) показания"
                    var latestReading = getActualReadings(getCurrentUser());
                    printStrings("Ваши актуальные показания (" + latestReading.getType() + "):\n");
                    printReadings(List.of(latestReading));
                }
                case 3 -> {                                                 //"3. Посмотреть показания за конкретный месяц"
                    var readingByMonth = getMonthReadings(getCurrentUser());
                    printStrings("Ваши показания за " + (readingByMonth.get(0).getMonth() - 1));
                    printReadings(readingByMonth);
                }
                case 4 -> {                                                 //"4. Посмотреть свою историю подачи показаний"
                    var readings = getHistoryReadings(getCurrentUser());
                    printStrings("Ваша история подачи показаний:");
                    printReadings(readings);
                }
                case 5 -> {                                                 //"5. Сменить пользователя"
                    if (showLogoutConfirm() == 1) {
                        logout();
                    }
                }
                case 6 -> {                                                 //"6. Выйти из программы"
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
            mainMenuChoice();  // Главное меню
        } else {
            if (isAdmin(getCurrentUser())) {
                adminMenuChoice(getCurrentUser());   // Меню админа
            } else {
                userMenuChoice(); // Меню пользователя
            }
        }
    }

    // Меню админа
    public static void adminMenuChoice(User currentUser) throws Exception {
        try {
            switch (showAdminMenu()) {
                case 1 -> sendReadings(currentUser);         //"1. Внести показания"
                case 2 -> {                                 //"2. Посмотреть актуальные(последние) показания"
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
                case 3 -> {                                 //"3. Посмотреть показания за конкретный месяц"
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
                case 4 -> {                                 //"4. Посмотреть историю подачи показаний"
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
                case 5 -> {                                 //"5. Посмотреть лог пользователя"
                    var logs = getUserLog(isRegistered(getTargetUsername()));
                    printStrings("Лог пользователя:");
                    printLogs(logs);
                }
                case 6 -> {                                 //"6. Расширить перечень показаний"
                    int result = showReadingsEdit();
                    if (result == 1) {
                        printStrings("Введите новый тип счетчика:");
                        addReadingType(readString());
                    } else if (result == 2) {
                        printStrings("Введите тип счетчика для удаления:");
                        deleteReadingType(getTypeOfReading());
                    }
                }
                case 7 -> {                                 //"7. Сменить пользователя"
                    if (showLogoutConfirm() == 1) {
                        logout();
                    }
                }
                case 8 -> {                                 //"8. Выход из программы"
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
            mainMenuChoice();  // Главное меню
        } else {
            if (isAdmin(getCurrentUser())) {
                adminMenuChoice(getCurrentUser());   // Меню админа
            } else {
                userMenuChoice(); // Меню пользователя
            }
        }
    }
}
