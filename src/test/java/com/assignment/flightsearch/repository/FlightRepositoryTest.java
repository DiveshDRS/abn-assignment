package com.assignment.flightsearch.repository;

import com.assignment.flightsearch.entity.Flight;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FlightRepositoryTest {
    @Mock
    private FlightRepository flightRepository;

    private final List<Flight> flights = setUpFlights();

    public List<Flight> setUpFlights() {
        Flight flight1 = new Flight();
        flight1.setFlightNumber("TEST01");
        flight1.setId(1L);
        flight1.setOrigin("BOM");
        flight1.setDestination("DEL");
        flight1.setDepartureTime("01:01");
        flight1.setArrivalTime("04:10");
        flight1.setDuration(189L);
        flight1.setPrice("100 EURO");

        Flight flight2 = new Flight();
        flight2.setFlightNumber("TEST02");
        flight2.setId(2L);
        flight2.setOrigin("DEL");
        flight2.setDestination("BOM");
        flight2.setDepartureTime("10:00");
        flight2.setArrivalTime("12:10");
        flight2.setDuration(130L);
        flight2.setPrice("80 EURO");

        return List.of(flight1, flight2);
    }

    @Test
    public void testFindByOriginAndDestination_thenOk() {

        String origin = "BOM";
        String dest = "DEL";

        Mockito.when(flightRepository.findByOriginAndDestination(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(flights));

        List<Flight> result = flightRepository.findByOriginAndDestination(origin, dest).orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TEST01", result.get(0).getFlightNumber());
        Assertions.assertEquals("TEST02", result.get(1).getFlightNumber());
    }
}
