package org.du6ak.services.out.menu;

import org.du6ak.services.MenuService;
import org.du6ak.services.exceptions.WrongOperationException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;


/**
 * This class contains the main menu of the program.
 */
public class MainMenu {
    private static final MainMenu INSTANCE = new MainMenu();

    public static MainMenu getInstance() {
        return INSTANCE;
    }

    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();
    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();
    private final MenuService menuService = MenuService.getInstance();
    private final MainMenu mainMenu = getInstance();

    /**
     * Prints a welcome message to the console.
     *
     * @throws Exception if an I/O error occurs
     */
    public void greetings() throws Exception {
        consoleWriterService.printStrings(
                "--------------------------------",
                "| Добро пожаловать в программу |",
                "--------------------------------\n"
        );
        menuService.mainMenuChoice();
    }

    /**
     * Displays the main menu and returns the user's choice.
     *
     * @return the user's choice
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public int showMainMenu() throws WrongOperationException {
        consoleWriterService.printStrings(
                "Выберите действие (введите номер операции от 1 до 3):",
                "1. Зарегистрироваться",
                "2. Войти",
                "3. Закрыть программу"
        );
        return consoleReaderService.readInt();
    }
}