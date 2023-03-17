package com.assignment.flightsearch.service;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.exception.FlightSearchException;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getFlightsBetweenOriginAndDestination(String origin, String destination, String sortBy) throws FlightSearchException;
}
