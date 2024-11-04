package com.littlepay.transit.utils;

import com.littlepay.transit.model.TapEvents;
import com.littlepay.transit.model.TapType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read tap events from a CSV file.
 *
 * @author Mohit Jain
 */

public class CsvReader {
    private static final String COMMA_DELIMITER = ",";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public List<TapEvents> readTaps(String fileName) {
        List<TapEvents> tapEvents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            //skip the headers from the csv file
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(COMMA_DELIMITER);
                TapEvents tapEvent = new TapEvents.TapEventsBuilder()
                        .id(Long.parseLong(fields[0].trim()))
                        .dateTime(LocalDateTime.parse(fields[1].trim(), DATE_TIME_FORMATTER))
                        .tapType(TapType.valueOf(fields[2].trim()))
                        .stopId(fields[3])
                        .companyId(fields[4].trim())
                        .busId(fields[5].trim())
                        .panId(fields[6].trim())
                        .build();
                tapEvents.add(tapEvent);
            }


        } catch (IOException exception) {
            throw new RuntimeException("Error reading the input file", exception);
        }
        return tapEvents;
    }
}
