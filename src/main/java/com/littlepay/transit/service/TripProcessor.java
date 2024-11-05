package com.littlepay.transit.service;

import com.littlepay.transit.model.TapEvents;
import com.littlepay.transit.model.TapType;
import com.littlepay.transit.model.Trip;
import com.littlepay.transit.model.TripStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


public class TripProcessor {
    private static final Logger log = LoggerFactory.getLogger(TripProcessor.class);


    private List<Trip> processTapEventsForPanId(List<TapEvents> panIdTaps){
        List<Trip> trips = new ArrayList<>();
        TapEvents previousTap = null;

        for (TapEvents currentTap: panIdTaps) {
            if ((TapType.ON).equals(currentTap.getTapType())){
               TapEvents matchingTapOff = findMatchingTapOff(panIdTaps, panIdTaps.indexOf(currentTap) + 1);
               trips.add(createTrip(currentTap, matchingTapOff));
               if (matchingTapOff == null) {
                   log.warn("No Matching Tap OFF found for tap id : {}", currentTap.getId());
               }
            } else if (previousTap == null || (TapType.OFF).equals(currentTap.getTapType())) {
                log.warn("Ignoring the Tap as no Matching Tap ON found for tap id : {}", currentTap.getId());
            }
            previousTap = currentTap;
        }
        return trips;
    }

    private Map<String, List<TapEvents>> groupAndSortTapEvents(List<TapEvents> tapEvents){
        return tapEvents.stream()
                .sorted(Comparator.comparing(TapEvents::getDateTime))
                .collect(Collectors.groupingBy(
                        TapEvents::getPanId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

    }

    private TapEvents findMatchingTapOff(List<TapEvents> panIdTaps, int startIndex) {
        return startIndex < panIdTaps.size() && (TapType.OFF).equals(panIdTaps.get(startIndex).getTapType()) ? panIdTaps.get(startIndex) : null;
    }

    // Version 2 of ProcessTrips
    public List<Trip> processTripsV2 (List<TapEvents> tapEvents) {
        var tapEventsByPanId = groupAndSortTapEvents(tapEvents);
        return  tapEventsByPanId.values().stream()
                .flatMap(panIdTaps -> processTapEventsForPanId(panIdTaps).stream())
                .collect(Collectors.toList());
    }

    // First Version of processTrips
    public List<Trip> processTrips(List<TapEvents> tapEvents) {
        Map<String, List<TapEvents>> tapEventsByPanId = tapEvents.stream()
                .sorted(Comparator.comparing(TapEvents::getDateTime))
                .collect(Collectors.groupingBy(
                        TapEvents::getPanId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
        List<Trip> trips = new ArrayList<>();
        tapEventsByPanId.forEach((panId, panIdTaps) -> {
            int i = 0;
            while (i < panIdTaps.size()) {
                TapEvents currentTap = panIdTaps.get(i);
                if ((TapType.ON).equals(panIdTaps.get(i).getTapType())) {
                    TapEvents matchingTapOff = null;
                    if (i + 1 < panIdTaps.size()) {
                        TapEvents nextTap = panIdTaps.get(i + 1);
                        if ((TapType.OFF).equals(nextTap.getTapType())) {
                            matchingTapOff = nextTap;
                            i += 2;
                        } else {
                            i++;
                        }
                    } else {
                        i++;
                    }
                    trips.add(createTrip(currentTap, matchingTapOff));
                } else {
                    log.info("Ignoring the Tap OFF as no previous Tap ON found for tap id :{}", currentTap.getId());
                    i++;
                }
            }
        });

        return trips;
    }

    private Trip createTrip(TapEvents tapOn, TapEvents tapOff) {

        Trip.TripBuilder builder = new Trip.TripBuilder()
                .startTime(tapOn.getDateTime())
                .fromStopId(tapOn.getStopId())
                .companyId(tapOn.getCompanyId())
                .busId(tapOn.getBusId())
                .panId(tapOn.getPanId());

        if (tapOff == null) {
            return builder
                    .endTime(tapOn.getDateTime())
                    .durationSecs(0L)
                    .toStopId("NO_TAP_OFF")
                    .chargeAmount(FareCalculator.getMaxFare(tapOn.getStopId()))
                    .status(TripStatus.INCOMPLETE)
                    .build();

        }

        if (tapOn.getStopId().equals(tapOff.getStopId())) {

            return builder
                    .endTime(tapOff.getDateTime())
                    .durationSecs(Duration.between(tapOn.getDateTime(), tapOff.getDateTime()).getSeconds())
                    .toStopId(tapOff.getStopId())
                    .chargeAmount(0.0)
                    .status(TripStatus.CANCELLED)
                    .build();
        }

        return builder
                .endTime(tapOff.getDateTime())
                .durationSecs(Duration.between(tapOn.getDateTime(), tapOff.getDateTime()).getSeconds())
                .chargeAmount(FareCalculator.calculateFare(tapOn.getStopId(), tapOff.getStopId()))
                .toStopId(tapOff.getStopId())
                .status(TripStatus.COMPLETED)
                .build();

    }
}
