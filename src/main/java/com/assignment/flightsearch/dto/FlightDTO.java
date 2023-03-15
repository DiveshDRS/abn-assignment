package com.assignment.flightsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@ToString
@NoArgsConstructor
public class FlightDTO {

    @NonNull
    private String flightNumber;

    @NonNull
    @NotNull(message = "{flight.origin.null}")
    @NotBlank(message = "{flight.origin.blank}")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "{flight.origin.format}")
    private String origin;

    @NonNull
    @NotNull(message = "{flight.destination.null}")
    @NotBlank(message = "{flight.destination.blank}")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "{flight.destination.format}")
    private String destination;

    @NonNull
    @NotNull(message = "{flight.departureTime.null}")
    @NotBlank(message = "{flight.departureTime.blank}")
    private String departureTime;

    @NonNull
    @NotNull(message = "{flight.arrivalTime.null}")
    @NotBlank(message = "{flight.arrivalTime.blank}")
    private String arrivalTime;

    private Long duration;

    @NonNull
    @NotNull(message = "{flight.price.null}")
    @NotBlank(message = "{flight.price.blank}")
    private String price;

    public Long calculateDuration(String departureTime, String arrivalTime) {
        return Duration.between(LocalTime.parse(departureTime), LocalTime.parse(arrivalTime)).toMinutes();
    }
}
