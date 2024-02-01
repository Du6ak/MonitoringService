package org.du6ak.repositories;

import org.du6ak.services.exceptions.AlreadyExistsException;
import org.du6ak.services.exceptions.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ReadingTypes {

    private static final ReadingTypes instance = new ReadingTypes();

    public static ReadingTypes getInstance() {
        return instance;
    }

    private List<String> readingTypes = new ArrayList<>();

    {
        readingTypes.add("холодная вода");
        readingTypes.add("горячая вода");
        readingTypes.add("электричество");
    }

    public List<String> getReadingTypes() {
        return readingTypes;
    }

    public void addReadingType(String type) throws AlreadyExistsException {
        if (readingTypes.contains(type)) {
            throw new AlreadyExistsException();
        }
        readingTypes.add(type);
    }

    public void deleteReadingType(String type) throws Exception {
        if (!readingTypes.contains(type)) {
            throw new DataNotFoundException();
        }
        readingTypes.remove(type);
    }
}
