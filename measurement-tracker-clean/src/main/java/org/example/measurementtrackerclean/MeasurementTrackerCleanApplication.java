package org.example.measurementtrackerclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeasurementTrackerCleanApplication {
    public static void main(String[] args) { SpringApplication.run(MeasurementTrackerCleanApplication.class, args); }
}
