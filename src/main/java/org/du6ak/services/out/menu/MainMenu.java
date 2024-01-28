package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;

import static org.du6ak.services.MenuService.mainMenuChoice;
import static org.du6ak.services.in.ConsoleReaderService.readInt;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;

/**
 * This class contains the main menu of the program.
 */
public class MainMenu {

    /**
     * Prints a welcome message to the console.
     *
     * @throws Exception if an I/O error occurs
     */
    public static void greetings() throws Exception {
        printStrings(
                "--------------------------------",
                "| Добро пожаловать в программу |",
                "--------------------------------\n"
        );
        mainMenuChoice();
    }

    /**
     * Displays the main menu and returns the user's choice.
     *
     * @return the user's choice
     * @throws WrongOperationException if the user enters an invalid choice
     */
    public static int showMainMenu() throws WrongOperationException {
        printStrings(
                "Выберите действие (введите номер операции от 1 до 3):",
                "1. Зарегистрироваться",
                "2. Войти",
                "3. Закрыть программу"
        );
        return readInt();
    }
}