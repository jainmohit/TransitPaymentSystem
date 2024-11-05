package com.littlepay.transit.service;

import com.littlepay.transit.model.TapEvents;
import com.littlepay.transit.model.TapType;
import com.littlepay.transit.model.Trip;
import com.littlepay.transit.model.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripProcessorTest {

    private TripProcessor tripProcessor;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    void setUp() {
        tripProcessor = new TripProcessor();
    }

    @Test
    void testProcessCompletedTrips() {
        List<TapEvents> tapEvents = new ArrayList<>(Arrays.asList(
                new TapEvents.TapEventsBuilder()
                        .id(1)
                        .dateTime(LocalDateTime.parse("22-01-2023 10:20:00", DATE_TIME_FORMATTER))
                        .tapType(TapType.ON)
                        .stopId("Stop1")
                        .companyId("Company1")
                        .busId("Bus1")
                        .panId("PAN1")
                        .build(),
                new TapEvents.TapEventsBuilder()
                        .id(2)
                        .dateTime(LocalDateTime.parse("22-01-2023 10:25:00", DATE_TIME_FORMATTER))
                        .tapType(TapType.OFF)
                        .stopId("Stop2")
                        .companyId("Company1")
                        .busId("Bus1")
                        .panId("PAN1")
                        .build())
        );

        List<Trip> trips = tripProcessor.processTrips(tapEvents);
        assertEquals(trips.size(), 1);
        assertEquals(trips.get(0).getPanId(), "PAN1");
        assertEquals(trips.get(0).getChargeAmount(), 3.25);
        assertEquals(trips.get(0).getStatus(), TripStatus.COMPLETED);
        assertEquals(trips.get(0).getDurationSecs(), 300);

    }

    @Test
    void testProcessCancelledTrips() {
        List<TapEvents> tapEvents = new ArrayList<>(Arrays.asList(
                new TapEvents.TapEventsBuilder()
                        .id(1)
                        .dateTime(LocalDateTime.parse("22-01-2023 10:20:00", DATE_TIME_FORMATTER))
                        .tapType(TapType.ON)
                        .stopId("Stop1")
                        .companyId("Company1")
                        .busId("Bus1")
                        .panId("PAN1")
                        .build(),
                new TapEvents.TapEventsBuilder()
                        .id(2)
                        .dateTime(LocalDateTime.parse("22-01-2023 10:25:00", DATE_TIME_FORMATTER))
                        .tapType(TapType.OFF)
                        .stopId("Stop1")
                        .companyId("Company1")
                        .busId("Bus1")
                        .panId("PAN1")
                        .build())
        );

        List<Trip> trips = tripProcessor.processTrips(tapEvents);
        assertEquals(trips.size(), 1);
        assertEquals(trips.get(0).getPanId(), "PAN1");
        assertEquals(trips.get(0).getChargeAmount(), 0.0);
        assertEquals(trips.get(0).getStatus(), TripStatus.CANCELLED);
        assertEquals(trips.get(0).getDurationSecs(), 300);

    }

    @Test
    void testProcessIncompleteTrips() {
        List<TapEvents> tapEvents = new ArrayList<>(Arrays.asList(
                new TapEvents.TapEventsBuilder()
                        .id(1)
                        .dateTime(LocalDateTime.parse("22-01-2023 10:20:00", DATE_TIME_FORMATTER))
                        .tapType(TapType.ON)
                        .stopId("Stop1")
                        .companyId("Company1")
                        .busId("Bus1")
                        .panId("PAN1")
                        .build()));

        List<Trip> trips = tripProcessor.processTrips(tapEvents);
        assertEquals(trips.size(), 1);
        assertEquals(trips.get(0).getPanId(), "PAN1");
        assertEquals(trips.get(0).getChargeAmount(), 7.3);
        assertEquals(trips.get(0).getStatus(), TripStatus.INCOMPLETE);
        assertEquals(trips.get(0).getDurationSecs(), 0);

    }

    @Test
    void testProcessIgnoredTaps() {
        List<TapEvents> tapEvents = new ArrayList<>(Arrays.asList(
                new TapEvents.TapEventsBuilder()
                        .id(1)
                        .dateTime(LocalDateTime.parse("22-01-2023 10:20:00", DATE_TIME_FORMATTER))
                        .tapType(TapType.OFF)
                        .stopId("Stop1")
                        .companyId("Company1")
                        .busId("Bus1")
                        .panId("PAN1")
                        .build())
        );

        List<Trip> trips = tripProcessor.processTrips(tapEvents);
        assertEquals(trips.size(), 0);

    }

}
