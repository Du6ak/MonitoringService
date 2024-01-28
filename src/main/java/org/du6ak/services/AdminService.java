package org.du6ak.services;

import org.du6ak.models.Log;
import org.du6ak.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.du6ak.services.MenuService.adminMenuChoice;
import static org.du6ak.services.MeterReadingService.*;
import static org.du6ak.services.UserService.*;
import static org.du6ak.services.UserService.isRegistered;
import static org.du6ak.services.out.ConsoleWriterService.printStrings;
import static org.du6ak.services.out.menu.AdminMenu.getTargetUsername;

public class AdminService {

    // Получить лог пользователя
    public static Queue<Log> getUserLog(User targetUser) throws Exception {
        if (targetUser == null) {
            throw new Exception("Пользователь не найден!");
        }
        Queue<Log> userLogs = LogService.getUsersLogs().get(targetUser);
        if (userLogs.isEmpty()) {
            throw new Exception("В базе нет логов!");
        }
        return userLogs;
    }

    // Добавить новый тип счетчика
    public static void addReadingType(String newReadingType) throws Exception {
        var readingTypes = getReadingTypes();
        if (readingTypes.contains(newReadingType)) {
            throw new Exception("Такой тип счетчика уже есть!");
        } else {
            addType(newReadingType);
        }
    }

    // Удалить тип счетчика
    public static void deleteReadingType(String removedType) throws Exception {
        if (removedType == null) {
            throw new Exception("Тип счетчика не указан!");
        }
        removeType(removedType);
    }
}
