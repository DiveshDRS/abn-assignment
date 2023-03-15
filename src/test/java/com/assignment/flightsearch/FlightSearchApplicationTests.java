package com.assignment.flightsearch;

import com.assignment.flightsearch.controller.FlightControllerTest;
import com.assignment.flightsearch.repository.FlightRepositoryTest;
import com.assignment.flightsearch.service.FlightServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FlightControllerTest.class,
        FlightRepositoryTest.class,
        FlightServiceTest.class
})
@SpringBootTest
class FlightSearchApplicationTests {


}
