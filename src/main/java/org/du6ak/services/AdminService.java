package org.du6ak.services;

import org.du6ak.models.Log;
import org.du6ak.models.User;

import java.util.Queue;

import static org.du6ak.services.MeterReadingService.*;

/**
 * Provides administrative functionality for the system, such as managing users, logs, and meter reading types.
 */
public class AdminService {

    /**
     * Retrieves the log for a specific user.
     *
     * @param targetUser the user for which to retrieve the log
     * @return the log for the specified user
     * @throws Exception if the user cannot be found or if there are no logs for the user
     */
    public static Queue<Log> getUserLog(User targetUser) throws Exception {
        if (targetUser == null) {
            throw new Exception("Пользователь не найден!");
        }
        Queue<Log> userLogs = LogService.getUsersLogs().get(targetUser);
        if (userLogs == null) {
            throw new Exception("В базе нет логов!");
        }
        return userLogs;
    }

    /**
     * Adds a new meter reading type to the system.
     *
     * @param newReadingType the type of meter reading to add
     * @throws Exception if the type of meter reading is already present in the system
     */
    public static void addReadingType(String newReadingType) throws Exception {
        if (getReadingTypes().contains(newReadingType)) {
            throw new Exception("Такой тип счетчика уже есть!");
        } else {
            addType(newReadingType);
        }
    }

    /**
     * Deletes a meter reading type from the system.
     *
     * @param removedType the type of meter reading to delete
     * @throws Exception if the type of meter reading is not present in the system
     */
    public static void deleteReadingType(String removedType) throws Exception {
        if (removedType == null) {
            throw new Exception("Тип счетчика не указан!");
        }
        removeType(removedType);
    }
}
