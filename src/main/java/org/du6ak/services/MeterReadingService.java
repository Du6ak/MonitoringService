package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.User;
import org.du6ak.repositories.Logs;
import org.du6ak.repositories.Users;
import org.du6ak.services.exceptions.DataNotFoundException;
import org.du6ak.services.exceptions.UserNotFoundException;
import org.du6ak.services.out.menu.ReadingMenu;

import java.time.Month;
import java.util.*;

/**
 * This class provides services for managing meter readings.
 */
@Data
public class MeterReadingService {

    private static final MeterReadingService INSTANCE = new MeterReadingService();
    public static MeterReadingService getInstance() {
        return INSTANCE;
    }

    private ReadingMenu readingMenu = ReadingMenu.getInstance();
    private Logs logs = Logs.getInstance();
    private Users users = Users.getInstance();

    /**
     * Adds a meter reading to the system.
     *
     * @param user the user submitting the reading
     * @throws Exception if there is an error processing the reading
     */
    public void sendReadings(User user, String type, Long contractNumber, int value, Month monthNumber) throws Exception {
        Reading newReading = new Reading(type, contractNumber, value, monthNumber); // create the new reading
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        Set<Reading> readings = users.getUsers().getOrDefault(user, new HashSet<>());
        if (!readings.contains(newReading)) {
            readings.add(newReading);
            logs.addLog(user, new Log("отправил показания (" + type + ")"));
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
    public Reading getActualReadings(User user, String type) throws Exception {
        if (user == null) {
            throw new UserNotFoundException();
        }
        Set<Reading> readings = users.getUsers().getOrDefault(user, new HashSet<>());
        if (readings != null) {
            Reading latestReading = null;
            for (Reading reading : readings) {
                if (reading.getType().equals(type) && latestReading == null) {
                    latestReading = reading;
                } else if (reading.getType().equals(type) && reading.getMonth().ordinal() > latestReading.getMonth().ordinal()) {
                    latestReading = reading;
                }
            }
            if (latestReading != null) {
                logs.addLog(user, new Log("посмотрел свои актуальные показания"));
                return latestReading;
            }
        }
        throw new DataNotFoundException();
    }

    /**
     * Returns the meter readings for a given month and user.
     *
     * @param user the user to retrieve readings for
     * @return the meter readings for the given month and user
     * @throws Exception if there is an error retrieving the readings
     */
    public List<Reading> getMonthReadings(User user, String type, Month month) throws Exception {
        if (user == null) {
            throw new UserNotFoundException();
        }
        Set<Reading> allReadings = users.getUsers().get(user);
        if (allReadings == null) {
            throw new DataNotFoundException();
        }
        List<Reading> monthReadings = new ArrayList<>();
        for (Reading reading : allReadings) {
            if (reading.getMonth().equals(month)) {
                monthReadings.add(reading);
            }
        }
        if (monthReadings.isEmpty()) {
            throw new DataNotFoundException();
        }
        logs.addLog(user, new Log("посмотрел свои показания за " + month));
        return monthReadings;
    }

    /**
     * Returns a user's meter reading history.
     *
     * @param user the user to retrieve the history for
     * @return the user's meter reading history
     * @throws Exception if there is an error retrieving the history
     */
    public List<Reading> getHistoryReadings(User user) throws Exception {
        if (user == null) {
            throw new UserNotFoundException();
        }
        List<Reading> readings = new ArrayList<>(users.getUsers().getOrDefault(user, new HashSet<>()));
        if (readings.isEmpty()) {
            throw new DataNotFoundException();
        }
        logs.addLog(user, new Log("посмотрел свою историю подачи показаний"));
        return readings;
    }
}
