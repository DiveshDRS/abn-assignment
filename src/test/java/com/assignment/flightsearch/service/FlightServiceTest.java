package com.assignment.flightsearch.service;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.entity.Flight;
import com.assignment.flightsearch.exception.FlightSearchException;
import com.assignment.flightsearch.repository.FlightRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private FlightServiceImpl flightService;

    private List<Flight> flights = setUpFlights();

    public List<Flight> setUpFlights() {
        Flight flight1 = new Flight();
        flight1.setFlightNumber("TEST01");
        flight1.setId(1L);
        flight1.setOrigin("BOM");
        flight1.setDestination("DEL");
        flight1.setDepartureTime("01:01");
        flight1.setArrivalTime("04:10");
        flight1.setDuration(189L);
        flight1.setPrice("150 EURO");

        Flight flight2 = new Flight();
        flight2.setFlightNumber("TEST02");
        flight2.setId(2L);
        flight2.setOrigin("DEL");
        flight2.setDestination("BOM");
        flight2.setDepartureTime("10:00");
        flight2.setArrivalTime("12:10");
        flight2.setDuration(130L);
        flight2.setPrice("80 EURO");

        Flight flight3 = new Flight();
        flight3.setFlightNumber("TEST03");
        flight3.setId(3L);
        flight3.setOrigin("DEL");
        flight3.setDestination("BOM");
        flight3.setDepartureTime("08:00");
        flight3.setArrivalTime("10:30");
        flight3.setDuration(150L);
        flight3.setPrice("120 EURO");

        Flight flight4 = new Flight();
        flight4.setFlightNumber("TEST04");
        flight4.setId(4L);
        flight4.setOrigin("BOM");
        flight4.setDestination("DEL");
        flight4.setDepartureTime("17:00");
        flight4.setArrivalTime("19:10");
        flight4.setDuration(130L);
        flight4.setPrice("75 EURO");

        return new LinkedList<>(List.of(flight1, flight2, flight3, flight4));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithoutSortBy_thenOk() throws FlightSearchException {
        String origin = "BOM";
        String dest = "DEL";
        String sortBy = "";

        flights.remove(1); //Remove 1st occurrence from list having DEL as origin
        flights.remove(1); //Remove 2nd occurrence from list having DEL as origin

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(flights));

        List<FlightDTO> result = flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TEST01", result.get(0).getFlightNumber());
        Assertions.assertEquals("TEST04", result.get(1).getFlightNumber());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithSortByPriceAsc_thenOk() throws FlightSearchException {
        String origin = "BOM";
        String dest = "DEL";
        String sortBy = "priceAsc";

        flights.remove(1); //Remove 1st occurrence from list having DEL as origin
        flights.remove(1); //Remove 2nd occurrence from list having DEL as origin

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(flights));

        List<FlightDTO> result = flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TEST04", result.get(0).getFlightNumber());
        Assertions.assertEquals("TEST01", result.get(1).getFlightNumber());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithSortByPriceDesc_thenOk() throws FlightSearchException {
        String origin = "BOM";
        String dest = "DEL";
        String sortBy = "priceDesc";

        flights.remove(1); //Remove 1st occurrence from list having DEL as origin
        flights.remove(1); //Remove 2nd occurrence from list having DEL as origin

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(flights));

        List<FlightDTO> result = flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TEST01", result.get(0).getFlightNumber());
        Assertions.assertEquals("TEST04", result.get(1).getFlightNumber());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithSortByDurationAsc_thenOk() throws FlightSearchException {
        String origin = "DEL";
        String dest = "BOM";
        String sortBy = "durationAsc";

        flights.remove(0); //Remove 1st occurrence from list having BOM as origin
        flights.remove(2); //Remove 2nd occurrence from list having BOM as origin

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(flights));

        List<FlightDTO> result = flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TEST02", result.get(0).getFlightNumber());
        Assertions.assertEquals("TEST03", result.get(1).getFlightNumber());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithSortByDurationDesc_thenOk() throws FlightSearchException {
        String origin = "DEL";
        String dest = "BOM";
        String sortBy = "durationDesc";

        flights.remove(0); //Remove 1st occurrence from list having BOM as origin
        flights.remove(2); //Remove 2nd occurrence from list having BOM as origin

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(flights));

        List<FlightDTO> result = flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TEST03", result.get(0).getFlightNumber());
        Assertions.assertEquals("TEST02", result.get(1).getFlightNumber());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationNoFlights1_then400() {
        String origin = "DE";
        String dest = "BOM";
        String sortBy = "";

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(Collections.emptyList()));

        Exception e = Assertions.assertThrows(FlightSearchException.class,
                () -> flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy));

        Assertions.assertEquals("service.flights.unavailable", e.getMessage());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationNoFlights2_then400() {
        String origin = "DEL";
        String dest = "BO";
        String sortBy = "";

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(Collections.emptyList()));

        Exception e = Assertions.assertThrows(FlightSearchException.class,
                () -> flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy));

        Assertions.assertEquals("service.flights.unavailable", e.getMessage());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationNullList_then400() {
        String origin = "DEL";
        String dest = "BO";
        String sortBy = "";

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(FlightSearchException.class,
                () -> flightService.getFlightsBetweenOriginAndDestination(origin, dest, sortBy));

        Assertions.assertEquals("general.error", e.getMessage());
    }

}
