package com.littlepay.transit.utils;

import com.littlepay.transit.model.Trip;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvWriter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void writeTrips(List<Trip> trips, String fileName) {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(fileName)) {
            fileWriter.append("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status\n");
            for (Trip trip : trips) {
                fileWriter.append(String.format("%s, %s, %d, %s, %s, %.2f, %s, %s, %s, %s\n",
                        trip.getStartTime().format(DATE_TIME_FORMATTER),
                        trip.getEndTime().format(DATE_TIME_FORMATTER),
                        trip.getDuration(),
                        trip.getFromStopId(),
                        trip.getToStopId(),
                        trip.getChargeAmount(),
                        trip.getCompanyId(),
                        trip.getBusId(),
                        trip.getPanId(),
                        trip.getStatus().name()
                ));
            }
        } catch (IOException exception) {
            throw new RuntimeException("Error writing trips to file ", exception);
        }
    }

}
