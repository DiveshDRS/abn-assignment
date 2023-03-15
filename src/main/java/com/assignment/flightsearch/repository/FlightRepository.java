package com.assignment.flightsearch.repository;

import com.assignment.flightsearch.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<List<Flight>> findByOriginAndDestination(String origin, String destination);
}
