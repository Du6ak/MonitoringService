package org.du6ak.services;

import lombok.Data;
import org.du6ak.models.Log;
import org.du6ak.models.Reading;
import org.du6ak.models.ReadingType;
import org.du6ak.models.User;
import org.du6ak.services.exceptions.AlreadyExistsException;
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

    /**
     * A menu for managing meter readings.
     */
    private ReadingMenu readingMenu = ReadingMenu.getInstance();

    /**
     * A log of user actions.
     */
    private Log log = Log.getInstance();

    /**
     * A user database.
     */
    private User user = User.getInstance();

    /**
     * A database of meter readings.
     */
    private Reading reading = Reading.getInstance();

    /**
     * A database of meter reading types.
     */
    private ReadingType readingType = ReadingType.getInstance();

    /**
     * Adds a meter reading to the database.
     *
     * @param username       the username of the user
     * @param typeId         the ID of the meter reading type
     * @param contractNumber the contract number of the meter
     * @param value          the value of the reading
     * @param monthNumber    the month of the reading
     * @throws UserNotFoundException  if the username is not found in the user database
     * @throws AlreadyExistsException if the reading already exists in the database
     */
    public void sendReadings(String username, int typeId, int contractNumber, int value, int monthNumber) throws Exception {
        if (!username.isEmpty() && user.isContains(username)) {
            if (reading.isContains(user.getUserId(username), typeId, contractNumber, value, monthNumber)) {
                throw new AlreadyExistsException();
            }
            reading.addReading(user.getUserId(username), typeId, contractNumber, value, monthNumber);
            log.addLog(user.getUserId(username), "внёс данные за " + monthNumber + " месяц по договору " + contractNumber);
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * Returns the latest meter reading for a user and meter type.
     *
     * @param username the username of the user
     * @param typeId   the ID of the meter reading type
     * @return the latest meter reading for the user and meter type
     * @throws UserNotFoundException if the username is not found in the user database
     * @throws DataNotFoundException if no reading exists for the user and meter type
     */
    public String getActualReadings(String username, int typeId) throws Exception {
        if (!username.isEmpty() && user.isContains(username)) {
            String latestReading = reading.getReading(user.getUserId(username), typeId);
            if (latestReading == null) {
                throw new DataNotFoundException();
            }
            log.addLog(user.getUserId(username), "посмотрел свои актуальные показания по счетчику " + readingType.getTypeName(typeId));
            return "\nПользователь: " + username +
                    "\nТип счетчика: " + readingType.getTypeName(typeId) +
                    latestReading;
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * Returns a list of meter readings for a user and month.
     *
     * @param username    the username of the user
     * @param typeId      the ID of the meter reading type
     * @param monthNumber the month number
     * @return a list of meter readings for the user and month
     * @throws UserNotFoundException if the username is not found in the user database
     * @throws DataNotFoundException if no readings exist for the user and month
     */
    public List<String> getMonthReadings(String username, int typeId, int monthNumber) throws Exception {
        if (!username.isEmpty() && user.isContains(username)) {
            List<String> monthReading = reading.getReadings(username, user.getUserId(username), typeId, monthNumber);
            if (monthReading.isEmpty()) {
                throw new DataNotFoundException();
            }
            log.addLog(user.getUserId(username), "посмотрел свои показания за " + Month.of(monthNumber).name());
            return monthReading;
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * Returns a list of meter readings for a user.
     *
     * @param username the username of the user
     * @return a list of meter readings for the user
     * @throws UserNotFoundException if the username is not found in the user database
     * @throws DataNotFoundException if no readings exist for the user
     */
    public List<String> getHistoryReadings(String username) throws Exception {
        if (!username.isEmpty() && user.isContains(username)) {
            List<String> historyReading = reading.getReadings(username, user.getUserId(username));
            if (historyReading.isEmpty()) {
                throw new DataNotFoundException();
            }
            log.addLog(user.getUserId(username), "посмотрел историю показаний");
            return historyReading;
        } else {
            throw new UserNotFoundException();
        }
    }
}