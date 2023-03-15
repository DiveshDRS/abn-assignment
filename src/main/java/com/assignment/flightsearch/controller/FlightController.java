package com.assignment.flightsearch.controller;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.exception.FlightSearchException;
import com.assignment.flightsearch.service.FlightService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@Validated
@Log4j2
public class FlightController {

    @Autowired
    private FlightService flightService;

    private static final String sortByRegex = "priceAsc|priceDesc|durationAsc|durationDesc|^[ ]*$";

    /**
     * This API returns list of flights between supplied Origin and Destination in unsorted form.
     * Also using an optional parameter sortBy, the results can be sorted based on the value provided.
     * Possible values in sortBy: priceAsc, priceDesc, durationAsc, durationDesc
     *
     * @param origin      - Origin of the flight
     * @param destination - Destination of the flight
     * @param sortBy      - Optional parameter, to sort the results
     * @return - List of flights, either in unsorted or sorted form based on sortBy parameter
     * @throws FlightSearchException        - Throws any application specific 4XX errors
     * @throws ConstraintViolationException - Throws 400 error if any params validation is violated
     */
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FlightDTO>> getFlightsBetweenOriginAndDest(@RequestParam(value = "origin") @NotNull(message = "{flight.origin.null}")
                                                                          @NotBlank(message = "{flight.origin.blank}") @Pattern(regexp = "[A-Za-z0-9]*", message = "{flight.origin.format}")
                                                                          String origin,
                                                                          @RequestParam(value = "dest") @NotNull(message = "{flight.destination.null}")
                                                                          @NotBlank(message = "{flight.destination.blank}") @Pattern(regexp = "[A-Za-z0-9]*", message = "{flight.destination.format}")
                                                                          String destination,
                                                                          @RequestParam(value = "sortBy", defaultValue = "", required = false) @Pattern(regexp = sortByRegex, message = "{controller.flights.sortBy.format}")
                                                                          String sortBy) throws FlightSearchException, ConstraintViolationException {

        long startTime = System.currentTimeMillis();
        log.debug("Entered getFlightsBetweenOriginAndDest controller");
        List<FlightDTO> flightDTOList = flightService.getFlightsBetweenOriginAndDestination(origin.toUpperCase(), destination.toUpperCase(), sortBy);
        long endTime = System.currentTimeMillis();
        log.info("getFlightsBetweenOriginAndDest controller completed in {}ms", (endTime - startTime));
        return ResponseEntity.ok(flightDTOList);
    }

}
