package com.assignment.flightsearch.validator;

import com.assignment.flightsearch.constants.PatternConstants;
import com.assignment.flightsearch.exception.FlightSearchException;

public class OriginValidator {

    public static void validateOrigin(String origin) throws FlightSearchException {
        if (null == origin) {
            throw new FlightSearchException("flight.origin.null");
        } else if (origin.isBlank() || origin.isEmpty()) {
            throw new FlightSearchException("flight.origin.blank");
        } else if (!origin.matches(PatternConstants.originRegex)) {
            throw new FlightSearchException("flight.origin.format");
        }
    }
}
