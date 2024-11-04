package com.littlepay.transit.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FareCalculatorTest {

    @Test
    void testCalculateFareForValidRoute() {
        assertEquals(FareCalculator.calculateFare("Stop1", "Stop2"), 3.25);
        assertEquals(FareCalculator.calculateFare("Stop2", "Stop3"), 5.50);
        assertEquals(FareCalculator.calculateFare("Stop1", "Stop3"), 7.30);
    }

    @Test
    void testCalculateFareForBiDirectionalRoute() {
        assertEquals(FareCalculator.calculateFare("Stop2", "Stop1"), FareCalculator.calculateFare("Stop1", "Stop2"));
        assertEquals(FareCalculator.calculateFare("Stop3", "Stop1"), FareCalculator.calculateFare("Stop1", "Stop3"));
    }

    @Test

    void testCalculateFareForNonTapOFFRoute() {
        assertEquals(7.30, FareCalculator.getMaxFare("Stop1"));
        assertEquals(5.50, FareCalculator.getMaxFare("Stop2"));
        assertEquals(7.30, FareCalculator.getMaxFare("Stop3"));
    }

}
