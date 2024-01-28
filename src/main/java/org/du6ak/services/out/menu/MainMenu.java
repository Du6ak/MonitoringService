package org.du6ak.services.out.menu;

import org.du6ak.services.exceptions.WrongOperationException;
import org.du6ak.services.in.ConsoleReaderService;
import org.du6ak.services.out.ConsoleWriterService;

import java.util.InputMismatchException;

import static org.du6ak.services.MenuService.mainMenuChoice;
import static org.du6ak.services.in.ConsoleReaderService.readInt;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;

public class MainMenu {
    //Приветствие при запуске программы
    public static void greetings() throws Exception {
        printStrings(
                "--------------------------------",
                "| Добро пожаловать в программу |",
                "--------------------------------\n"
        );
        mainMenuChoice();// Стартуем с главного меню
    }

    // Главное меню
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