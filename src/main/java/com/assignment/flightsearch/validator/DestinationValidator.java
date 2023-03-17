package com.assignment.flightsearch.validator;

import com.assignment.flightsearch.constants.PatternConstants;
import com.assignment.flightsearch.exception.FlightSearchException;

public class DestinationValidator {

    public static void validateDestination(String destination) throws FlightSearchException {
        if (null == destination) {
            throw new FlightSearchException("flight.destination.null");
        } else if (destination.isBlank() || destination.isEmpty()) {
            throw new FlightSearchException("flight.destination.blank");
        } else if (!destination.matches(PatternConstants.destinationRegex)) {
            throw new FlightSearchException("flight.destination.format");
        }
    }
}
