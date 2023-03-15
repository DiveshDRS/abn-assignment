package com.assignment.flightsearch.service;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.entity.Flight;
import com.assignment.flightsearch.exception.FlightSearchException;
import com.assignment.flightsearch.repository.FlightRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * This method returns a list of flights as per provided origin and destination in unsorted form if sortBy is not provided.
     * Based on the value in optional sortBy parameter, the relevant method to sort the results is called
     *
     * @param origin      - Origin of the flight
     * @param destination - Destination of the flight
     * @param sortBy      - Optional parameter, to sort the results
     * @return - List of flights, either in unsorted or sorted form based on sortBy parameter
     * @throws FlightSearchException - Throws any application specific 4XX errors
     */
    @Override
    public List<FlightDTO> getFlightsBetweenOriginAndDestination(String origin, String destination, String sortBy) throws FlightSearchException {
        long startTime = System.currentTimeMillis();
        log.debug("Entered getFlightsBetweenOriginAndDestination service");
        Optional<List<Flight>> optionalFlights = flightRepository.findByOriginAndDestination(origin, destination);
        List<Flight> flightList = optionalFlights.orElseThrow(() -> new FlightSearchException("general.error"));
        if (flightList.isEmpty())
            throw new FlightSearchException("service.flights.unavailable");
        List<FlightDTO> flightListDTO = mapEntityToDTO(flightList);
        long endTime = System.currentTimeMillis();
        log.debug("getFlightsBetweenOriginAndDestination service completed in {}ms", (endTime - startTime));
        return switch (sortBy) {
            case "priceAsc" -> getFlightsBetweenOriginAndDestinationSortByPriceAsc(origin, destination, flightListDTO);
            case "priceDesc" ->
                    getFlightsBetweenOriginAndDestinationSortByPriceDesc(origin, destination, flightListDTO);
            case "durationAsc" ->
                    getFlightsBetweenOriginAndDestinationSortByDurationAsc(origin, destination, flightListDTO);
            case "durationDesc" ->
                    getFlightsBetweenOriginAndDestinationSortByDurationDesc(origin, destination, flightListDTO);
            default -> flightListDTO;
        };
    }

    /**
     * Returns list of flights between provided origin and destination in sorted order based on price of the flight
     *
     * @param origin        - Origin of the flight
     * @param destination   - Destination of the flight
     * @param flightListDTO - Flight DTO object to perform operations
     * @return - List of flights, in sorted form based on price
     */
    @Override
    public List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByPriceAsc(String origin, String destination, List<FlightDTO> flightListDTO) {
        return flightListDTO.stream()
                .sorted(Comparator.comparing(FlightDTO::getPrice).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Returns list of flights between provided origin and destination in reverse sorted order based on price of the flight
     *
     * @param origin        - Origin of the flight
     * @param destination   - Destination of the flight
     * @param flightListDTO - Flight DTO object to perform operations
     * @return - List of flights, in reverse sorted form based on price
     */
    @Override
    public List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByPriceDesc(String origin, String destination, List<FlightDTO> flightListDTO) {
        return flightListDTO.stream()
                .sorted(Comparator.comparing(FlightDTO::getPrice))
                .collect(Collectors.toList());
    }

    /**
     * Returns list of flights between provided origin and destination in sorted order based on duration of the flight
     *
     * @param origin        - Origin of the flight
     * @param destination   - Destination of the flight
     * @param flightListDTO - Flight DTO object to perform operations
     * @return - List of flights, in sorted form based on duration
     */
    @Override
    public List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByDurationAsc(String origin, String destination, List<FlightDTO> flightListDTO) {
        return flightListDTO.stream()
                .sorted(Comparator.comparing(FlightDTO::getDuration))
                .collect(Collectors.toList());
    }

    /**
     * Returns list of flights between provided origin and destination in reverse sorted order based on duration of the flight
     *
     * @param origin        - Origin of the flight
     * @param destination   - Destination of the flight
     * @param flightListDTO - Flight DTO object to perform operations
     * @return - List of flights, in reverse sorted form based on duration
     */
    @Override
    public List<FlightDTO> getFlightsBetweenOriginAndDestinationSortByDurationDesc(String origin, String destination, List<FlightDTO> flightListDTO) {
        return flightListDTO.stream()
                .sorted(Comparator.comparing(FlightDTO::getDuration).reversed())
                .collect(Collectors.toList());
    }

    public List<FlightDTO> mapEntityToDTO(List<Flight> flightList) throws FlightSearchException {
        try {
            return flightList.stream()
                    .map(flight -> modelMapper.map(flight, FlightDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.toString());
            throw new FlightSearchException(e.getMessage());
        }
    }
}
