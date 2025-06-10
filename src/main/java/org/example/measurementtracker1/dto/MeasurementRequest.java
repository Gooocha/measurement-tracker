package org.example.measurementtracker1.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeasurementRequest {

    @Min(1)
    private long userId;


    @NotNull(message = "cold water value is required")
    @Min(value = 0, message = "Cold water must be >= 0")
    @Max(value = 1000, message = "Cold water must be <= 1000")
    private Double coldWater;


    @NotNull(message = "hot water  value is required")
    @Min(value = 0, message = "Hot water must be >= 0")
    @Max(value = 100, message = "Hot water must be <= 100")
    private Double hotWater;


    @NotNull(message = "Gas value is required")
    @Min(value = 0, message = "Gas must be >= 0")
    @Max(value = 1000, message = "Gas must be <= 1000")
    private Double gas;


    @NotNull
    private LocalDateTime timestamp;


}
