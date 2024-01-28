package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.User;

import java.util.*;

import static org.du6ak.services.LogService.addLog;
import static org.du6ak.services.out.menu.ReadingMenu.*;
import static org.du6ak.services.UserService.*;

/**
 * This class provides services for managing meter readings.
 */
@Data
public class MeterReadingService {
    /**
     * A list of supported meter reading types.
     */
    private static List<String> readingTypes = List.of("холодная вода", "горячая вода", "электричество");

    /**
     * A list of month names.
     */
    public static final List<String> MONTHS = List.of("январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь");

    /**
     * Returns a list of supported meter reading types.
     *
     * @return a list of supported meter reading types
     */
    public static List<String> getReadingTypes() {
        return readingTypes;
    }

    /**
     * Adds a meter reading type to the list of supported types.
     *
     * @param type the meter reading type to add
     */
    public static void addType(String type) {
        readingTypes.add(type);
    }

    /**
     * Removes a meter reading type from the list of supported types.
     *
     * @param type the meter reading type to remove
     */
    public static void removeType(String type) {
        readingTypes.remove(type);
    }

    /**
     * Adds a meter reading to the system.
     *
     * @param user the user submitting the reading
     * @throws Exception if there is an error processing the reading
     */
    public static void sendReadings(User user) throws Exception {
        String type = getTypeOfReading(); // get the meter reading type
        Long contractNumber = getContractNumber(); // get the contract number
        int value = getValue(); // get the reading value
        int month = getMonthOfReading(); // get the month of the reading
        Reading newReading = new Reading(type, contractNumber, value, month); // create the new reading
        if (Objects.isNull(user)) {
            throw new Exception("Ошибка! user not found");
        }
        Set<Reading> readings = getUsers().getOrDefault(user, new HashSet<>());
        if (!readings.contains(newReading)) {
            readings.add(newReading);
            addLog(user, new Log("отправил показания (" + type + ")"));
        } else {
            throw new Exception("Вы уже отправляли показания со счетчика " + type + " этом месяце!");
        }
    }

    /**
     * Returns the latest meter reading for a given user and meter type.
     *
     * @param user the user to retrieve readings for
     * @return the latest meter reading for the user and meter type
     * @throws Exception if there is an error retrieving the readings
     */
    public static Reading getActualReadings(User user) throws Exception {
        if (user != null) {
            String type = getTypeOfReading(); // get the meter reading type
            Set<Reading> readings = getUsers().getOrDefault(user, new HashSet<>());
            if (readings.isEmpty()) {
                throw new Exception("В базе нет показаний по счетчику " + type + "!");
            } else {
                Reading latestReading = null;
                for (Reading reading : readings) {
                    if (reading.getType().equals(type) && latestReading == null) {
                        latestReading = reading;
                    } else if (reading.getType().equals(type) && reading.getMonth() > latestReading.getMonth()) {
                        latestReading = reading;
                    }
                }
                if (latestReading == null) {
                    throw new Exception("В базе нет ваших показаний по счетчику " + type + "!");
                } else {
                    addLog(user, new Log("посмотрел свои актуальные показания"));
                    return latestReading;
                }
            }
        } else {
            throw new Exception("Ошибка! user not found");
        }
    }

    /**
     * Returns the meter readings for a given month and user.
     *
     * @param user the user to retrieve readings for
     * @return the meter readings for the given month and user
     * @throws Exception if there is an error retrieving the readings
     */
    public static List<Reading> getMonthReadings(User user) throws Exception {
        if (user != null) {
            int month = getMonthOfReading(); // get the month of the readings
            Set<Reading> readings = getUsers().getOrDefault(user, new HashSet<>());
            if (readings.isEmpty()) {
                throw new Exception("В базе нет ваших показаний за " + MONTHS.get(month - 1) + " (" + month + ")!");
            }
            List<Reading> readingList = new ArrayList<>();
            for (Reading reading : readings) {
                if (reading.getMonth() == month) {
                    readingList.add(reading);
                }
            }
            if (readingList.isEmpty()) {
                throw new Exception("В базе нет ваших показаний за " + MONTHS.get(month - 1) + " (" + month + ")!");
            }
            addLog(user, new Log("посмотрел свои показания за " + MONTHS.get(month - 1)));
            return readingList;

        } else {
            throw new Exception("Ошибка! user not found");
        }
    }

    /**
     * Returns a user's meter reading history.
     *
     * @param user the user to retrieve the history for
     * @return the user's meter reading history
     * @throws Exception if there is an error retrieving the history
     */
    public static List<Reading> getHistoryReadings(User user) throws Exception {
        if (user != null) {
            List<Reading> readings = new ArrayList<>(getUsers().getOrDefault(user, new HashSet<>()));
            if (readings.isEmpty()) {
                throw new Exception("Ваша история подачи показаний пуста!");
            } else {
                addLog(user, new Log("посмотрел свою историю подачи показаний"));
                return readings;
            }
        } else {
            throw new Exception("Ошибка! user not found");
        }
    }

    /**
     * Displays a menu of meter reading types and returns the user's selection.
     *
     * @return the user's selection
     * @throws Exception if there is an error displaying the menu
     */
    public static String getTypeOfReading() throws Exception {
        int typeIndex = showReadingMenu() - 1;
        if (typeIndex >= 0 && typeIndex < readingTypes.size()) {
            return readingTypes.get(typeIndex);
        } else {
            throw new Exception("Неверный номер операции!");
        }
    }

    /**
     * Checks if a user is an admin.
     *
     * @param user the user to check
     * @return true if the user is an admin, false otherwise
     */
    public static boolean isAdmin(User user) {
        return user != null && user.isAdmin();
    }

    /**
     * Displays a menu of month options and returns the user's selection.
     *
     * @return the user's selection
     * @throws Exception if there is an error displaying the menu
     */
    public static int getMonthOfReading() throws Exception {
        int monthNumber = getMonth();
        if (monthNumber >= 1 && monthNumber <= 12) {
            return monthNumber;
        } else {
            throw new Exception("Неверный номер месяца!");
        }
    }
}
