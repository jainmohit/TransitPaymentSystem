package com.littlepay.transit.utils;

import com.littlepay.transit.model.TapEvents;
import com.littlepay.transit.model.TapType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CsvReader {
    private static final String COMMA_DELIMITER = ",";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(CsvReader.class);

    public List<TapEvents> readTaps(String fileName) {

        log.info("Reading the input file : {}", fileName);

        List<TapEvents> tapEvents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("csvFiles/input/" + fileName))) {
            reader.readLine();

            String line;
            while((line = reader.readLine()) != null) {
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
            log.info("Reading the input file : {} Done", fileName);

        } catch (IOException exception) {
            throw new RuntimeException("Error reading the input file", exception);
        }
        return tapEvents;
    }
}
