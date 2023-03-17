package com.assignment.flightsearch.validator;

import com.assignment.flightsearch.constants.PatternConstants;
import com.assignment.flightsearch.exception.FlightSearchException;

public class SortByValidator {

    public static void validateSortBy(String sortBy) throws FlightSearchException {
        if (!sortBy.matches(PatternConstants.sortByRegex)) {
            throw new FlightSearchException("controller.flights.sortBy.format");
        }
    }
}
