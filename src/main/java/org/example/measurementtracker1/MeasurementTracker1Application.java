package org.example.measurementtracker1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        basePackages = "org.example.measurementtracker1",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "org\\.example\\.measurementtracker1\\.controller\\.MeterController"
        )
)
public class MeasurementTracker1Application {
    public static void main(String[] args) {
        SpringApplication.run(MeasurementTracker1Application.class, args);
    }
}
