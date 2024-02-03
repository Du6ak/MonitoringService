package org.du6ak.services.out.menu;

import org.du6ak.services.MenuService;
import org.du6ak.services.exceptions.IncorrectDataException;
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

    /**
     * A service for writing strings to the console.
     */
    private final ConsoleWriterService consoleWriterService = ConsoleWriterService.getInstance();

    /**
     * A service for reading strings from the console.
     */
    private final ConsoleReaderService consoleReaderService = ConsoleReaderService.getInstance();

    /**
     * A service for managing the main menu options.
     */
    private final MenuService menuService = MenuService.getInstance();

    /**
     * Prints the greetings to the console.
     *
     * @throws Exception if an error occurs while writing to the console.
     */
    public void greetings() throws Exception {
        consoleWriterService.printStrings(
                "--------------------------------",
                "| Добро пожаловать в программу |",
                "--------------------------------"
        );
        menuService.mainMenuChoice();
    }

    /**
     * Shows the main menu to the user and returns their choice.
     *
     * @return the user's choice from the main menu.
     * @throws IncorrectDataException if the user enters an invalid choice.
     */
    public int showMainMenu() throws IncorrectDataException {
        consoleWriterService.printStrings(
                "\nВыберите действие (введите номер операции от 1 до 3):",
                "1. Зарегистрироваться",
                "2. Войти",
                "3. Закрыть программу"
        );
        return consoleReaderService.readInt();
    }
}