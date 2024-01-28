package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.User;

import java.util.*;

import static org.du6ak.services.LogService.*;
import static org.du6ak.services.out.menu.UserMenu.*;

@Data
public class UserService {
    private static final HashMap<User, Set<Reading>> users = new HashMap<>();   //список пользователей
    private static final List<String> ROLES = List.of("admin", "administrator", "админ", "администратор"); // Список ролей
    private static User currentUser;    // Текущий пользователь (авторизованный)

    public static Map<User, Set<Reading>> getUsers() {
        return users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User newUser) {
        UserService.currentUser = newUser;
    }

    // Регистрация пользователя
    public static void registration(String username, String password) throws Exception {
        if (currentUser != null) {
            throw new Exception("Невозможно зарегистрироваться!\nВы уже авторизованы как " + currentUser.getUsername() + "!");
        }
        User newUser = new User(username, password, ROLES.contains(username.toLowerCase()));
        if (users.keySet().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            throw new Exception("Такой пользователь уже зарегистрирован!\nПопробуйте еще раз");
        }
        users.put(newUser, new HashSet<>());                           // Регистрируем пользователя
        addLog(newUser, new Log("зарегистрировался"));   // Записываем лог
    }

    // Авторизация пользователя
    public static void login(String username, String password) throws Exception {
        if (currentUser != null) {
            throw new Exception("Вы уже авторизованы!");
        }
        if (users.isEmpty()) {
            throw new Exception("Пользователь " + username + " не найден в базе.\nПожалуйста, пройдите регистрацию!");
        }
        currentUser = users.keySet().stream().filter(user -> user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)).findFirst()
                .orElseThrow(() -> new Exception("Неправильный логин или пароль!\nПопробуйте еще раз"));
        addLog(currentUser, new Log("авторизировался"));// Записываем лог
    }

    // Выход из учетной записи
    public static void logout() throws Exception {
        if (currentUser == null) {
            throw new Exception("Вы не авторизованы!");
        }
        addLog(currentUser, new Log("вышел из учетной записи")); // Записываем лог
        setCurrentUser(null); // Обнуляем авторизированного пользователя
    }

    // Проверка на регистрацию
    public static User isRegistered(String targetUsername) {
        return users.keySet().stream().filter(user -> user.getUsername().equalsIgnoreCase(targetUsername)).findFirst().orElse(null);
    }

    // Выход из программы
    public static void exit() {
        System.exit(1);
    }

}
