package com.assignment.flightsearch.service;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.exception.FlightSearchException;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getFlightsBetweenOriginAndDestination(String origin, String destination, String sortBy) throws FlightSearchException;

    List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByPriceAsc(String origin, String destination, List<FlightDTO> flightListDTO);

    List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByPriceDesc(String origin, String destination, List<FlightDTO> flightListDTO);

    List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByDurationAsc(String origin, String destination, List<FlightDTO> flightListDTO);

    List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByDurationDesc(String origin, String destination, List<FlightDTO> flightListDTO);
}
