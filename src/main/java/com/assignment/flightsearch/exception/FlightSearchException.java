package com.assignment.flightsearch.exception;

import java.io.Serial;

public class FlightSearchException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public FlightSearchException(String message) {
        super(message);
    }
}
