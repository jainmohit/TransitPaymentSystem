package com.littlepay.transit.service;

import java.util.HashMap;
import java.util.Map;

public class FareCalculator {

    private static final Map<String, HashMap<String, Double>> FARE_MATRIX = new HashMap<>();
    private static final Map<String, Double> MAX_FARES = new HashMap<>();

    static {
        FARE_MATRIX.put("Stop1", new HashMap<>());
        FARE_MATRIX.put("Stop2", new HashMap<>());
        FARE_MATRIX.put("Stop3", new HashMap<>());

        setFare("Stop1", "Stop2", 3.25);
        setFare("Stop1", "Stop3", 7.30);
        setFare("Stop2", "Stop3", 5.50);

        calculateMaxFares();
    }

    private static void setFare(String  fromStop, String toStop, double fare) {
        FARE_MATRIX.get(fromStop).put(toStop, fare);
        FARE_MATRIX.get(toStop).put(fromStop, fare);
    }

    private static void calculateMaxFares() {
        for (String stopId : FARE_MATRIX.keySet()) {
            MAX_FARES.put(stopId, FARE_MATRIX.get(stopId).values().stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(0.0));
        }
    }

    public static double calculateFare(String fromStop, String toStop) {
       return FARE_MATRIX.get(fromStop).getOrDefault(toStop, 0.0);
    }

    public static double getMaxFare(String stopId) {
        return MAX_FARES.getOrDefault(stopId, 0.0);
    }
}
