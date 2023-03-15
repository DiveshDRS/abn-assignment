package com.assignment.flightsearch.dataloader;

import com.assignment.flightsearch.dto.FlightDTO;
import com.assignment.flightsearch.entity.Flight;
import com.assignment.flightsearch.repository.FlightRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@Log4j2
@Transactional
public class DataLoader {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${csv.path}")
    private String csvPath;

    /**
     * This method loads data from CSV into in-memory H2 database.
     * This method runs as soon as the application starts.
     */
    @PostConstruct
    public void loadData() {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(new File("").getAbsolutePath() + csvPath)).withSkipLines(1).build()) {
            List<String[]> data = csvReader.readAll();
            for (String[] row : data) {
                FlightDTO flightDTO = new FlightDTO(row[0], row[1], row[2], row[3], row[4], row[5]);
                flightDTO.setDuration(flightDTO.calculateDuration(row[3], row[4]));
                Flight flight = modelMapper.map(flightDTO, Flight.class);
                flightRepository.save(flight);
            }
        } catch (IOException | CsvException | DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

}
