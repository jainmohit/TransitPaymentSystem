package com.littlepay.transit;

import com.littlepay.transit.model.TapEvents;
import com.littlepay.transit.model.Trip;
import com.littlepay.transit.service.TripProcessor;
import com.littlepay.transit.utils.CsvReader;
import com.littlepay.transit.utils.CsvWriter;

import java.util.List;

public class TransitApp
{
    public static void main( String[] args )
    {
        CsvReader csvReader = new CsvReader();
        CsvWriter  csvWriter = new CsvWriter();
        TripProcessor tripProcessor = new TripProcessor();
        List<TapEvents> tapEvents = csvReader.readTaps("taps.csv");
        List<Trip> trips = tripProcessor.processTrips(tapEvents);
        csvWriter.writeTrips(trips, "trips.csv");
    }
}
