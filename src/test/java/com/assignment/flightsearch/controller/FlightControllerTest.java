package com.assignment.flightsearch.controller;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FlightService flightService;

    private final List<FlightDTO> flightDTOs = setupFlightDTOs();

    @Autowired
    private Environment environment;

    public List<FlightDTO> setupFlightDTOs() {
        FlightDTO flightDTO1 = new FlightDTO();
        flightDTO1.setFlightNumber("TEST01");
        flightDTO1.setOrigin("BOM");
        flightDTO1.setDestination("DEL");
        flightDTO1.setDepartureTime("01:01");
        flightDTO1.setArrivalTime("04:10");
        flightDTO1.setDuration(189L);
        flightDTO1.setPrice("100 EURO");

        FlightDTO flightDTO2 = new FlightDTO();
        flightDTO2.setFlightNumber("TEST02");
        flightDTO2.setOrigin("DEL");
        flightDTO2.setDestination("BOM");
        flightDTO2.setDepartureTime("10:00");
        flightDTO2.setArrivalTime("12:10");
        flightDTO2.setDuration(130L);
        flightDTO2.setPrice("80 EURO");

        return List.of(flightDTO1, flightDTO2);
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithoutSortBy_thenOk() throws Exception {
        String origin = "AMS";
        String dest = "DEL";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").isNotEmpty());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationWithSortBy_thenOk() throws Exception {
        String origin = "AMS";
        String dest = "DEL";
        String sortBy = "priceAsc";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest)
                        .param("sortBy", sortBy))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").isNotEmpty());
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationEmptyOrigin_then400() throws Exception {
        String origin = "";
        String dest = "DEL";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("flight.origin.blank")));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationInvalidOrigin_then400() throws Exception {
        String origin = "@";
        String dest = "DEL";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("flight.origin.format")));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationNotPassingOrigin_then400() throws Exception {
        String dest = "DEL";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("dest", dest))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("flight.origin.null")));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationEmptyDest_then400() throws Exception {
        String origin = "AMS";
        String dest = "";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("flight.destination.blank")));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationInvalidDest_then400() throws Exception {
        String origin = "AMS";
        String dest = "@";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("flight.destination.format")));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationNotPassingDest_then400() throws Exception {
        String origin = "AMS";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("flight.destination.null")));
    }

    @Test
    public void testGetFlightsBetweenOriginAndDestinationInvalidSortBy_then400() throws Exception {
        String origin = "AMS";
        String dest = "BOM";
        String sortBy = "InvalidValue";

        Mockito.when(flightService.getFlightsBetweenOriginAndDestination(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(flightDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("origin", origin)
                        .param("dest", dest)
                        .param("sortBy", sortBy))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].flightNumber").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(environment.getProperty("controller.flights.sortBy.format")));
    }
}
