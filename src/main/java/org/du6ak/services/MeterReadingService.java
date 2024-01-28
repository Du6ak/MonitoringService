package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.User;

import java.util.*;

import static org.du6ak.services.LogService.addLog;
import static org.du6ak.services.out.menu.ReadingMenu.*;
import static org.du6ak.services.UserService.*;

@Data
public class MeterReadingService {
    //    private static List<Reading> meterReadings = new ArrayList<>();//список показаний
    private static List<String> readingTypes = List.of("холодная вода", "горячая вода", "электричество");
    public static final List<String> MONTHS = List.of("январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь");

    public static List<String> getReadingTypes() {
        return readingTypes;
    }

    public static void addType(String type) {
        readingTypes.add(type);
    }
    public static void removeType(String type) {
        readingTypes.remove(type);
    }


    //Внести показания
    public static void sendReadings(User user) throws Exception {
        String type = getTypeOfReading(); // получаем тип счетчика
        Long contractNumber = getContractNumber();// Получаем номер контракта
        int value = getValue();// Получаем показание счётчика
        int month = getMonthOfReading();// Получаем номер месяца
        Reading newReading = new Reading(type, contractNumber, value, month); // Создаём наше показание
        if (Objects.isNull(user)) {
            throw new Exception("Ошибка! user not found");
        }
        Set<Reading> readings = getUsers().getOrDefault(user, new HashSet<>());
        if (!readings.contains(newReading)) {
            readings.add(newReading);
            addLog(user, new Log("отправил показания (" + type + ")"));   // Записываем лог
        } else {
            throw new Exception("Вы уже отправляли показания со счетчика " + type + " этом месяце!");
        }
    }
    // send - c null юзером - о р = Exception
    // 2ой тест - добавление ридингс с валидными данными - ор - регистрация прошла
    // 3ий тест - 2 вызова send  одинаковыми данными - ор - 1ый вызорв - тру, 2ой - Эксепшн

    //Посмотреть актуальные показания
    public static Reading getActualReadings(User user) throws Exception {
        if (user != null) {
            String type = getTypeOfReading(); // получаем тип счетчика
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
                    addLog(user, new Log("посмотрел свои актуальные показания"));   // Записываем лог
                    return latestReading;
                }
            }
        } else {
            throw new Exception("Ошибка! user not found");
        }
    }

    //Посмотреть показания за месяц
    public static List<Reading> getMonthReadings(User user) throws Exception {
        if (user != null) {
            int month = getMonthOfReading();// Получаем номер месяца
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
            addLog(user, new Log("посмотрел свои показания за " + MONTHS.get(month - 1)));   // Записываем лог
            return readingList;

        } else {
            throw new Exception("Ошибка! user not found");
        }
    }

    //Посмотреть историю подачи показаний
    public static List<Reading> getHistoryReadings(User user) throws Exception {
        if (user != null) {
            List<Reading> readings = new ArrayList<>(getUsers().getOrDefault(user, new HashSet<>()));
            if (readings.isEmpty()) {
                throw new Exception("Ваша история подачи показаний пуста!");
            } else {
                addLog(user, new Log("посмотрел свою историю подачи показаний"));   // Записываем лог
                return readings;
            }
        } else {
            throw new Exception("Ошибка! user not found");
        }
    }

    // Показать тип счетчика
    public static String getTypeOfReading() throws Exception {
        int typeIndex = showReadingMenu() - 1;
        if (typeIndex >= 0 && typeIndex < readingTypes.size()) {
            return readingTypes.get(typeIndex);
        } else {
            throw new Exception("Неверный номер операции!");
//            return getTypeOfReading(); // получаем тип счетчика
        }
    }

    // Текущий пользователь админ?
    public static boolean isAdmin(User user) {
        return user != null && user.isAdmin();
    }

    // Получить номер месяца
    public static int getMonthOfReading() throws Exception {
        int monthNumber = getMonth();
        if (monthNumber >= 1 && monthNumber <= 12) {
            return monthNumber;
        } else {
            throw new Exception("Неверный номер месяца!");
        }
    }
}
