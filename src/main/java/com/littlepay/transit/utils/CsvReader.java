package com.littlepay.transit.utils;

import com.littlepay.transit.model.TapEvents;
import com.littlepay.transit.model.TapType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if(fileReader == null){
                throw new RuntimeException("File not found in resources " + fileName);
            }
            //skip the headers from the csv file
            fileReader.readLine();

            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] fields = line.split(COMMA_DELIMITER);
                TapEvents tapEvent = new TapEvents.TapEventsBuilder()
                        .id(Long.parseLong(fields[0].trim()))
                        .dateTime(LocalDateTime.parse(fields[1].trim(), DATE_TIME_FORMATTER))
                        .tapType(TapType.valueOf(fields[2].trim()))
                        .stopId(fields[3].trim())
                        .companyId(fields[4].trim())
                        .busId(fields[5].trim())
                        .panId(fields[6].trim())
                        .build();
                tapEvents.add(tapEvent);
            }


        } catch (IOException exception) {
            throw new RuntimeException("Error reading the input file " + fileName, exception);
        } catch (NumberFormatException | DateTimeParseException exception) {
            throw new RuntimeException("Error parsing the input file " + fileName, exception);
        }
        return tapEvents;
    }
}
