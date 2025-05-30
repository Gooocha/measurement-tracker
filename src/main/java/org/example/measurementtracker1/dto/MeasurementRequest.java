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

    @PositiveOrZero
    private double coldWater;

    @PositiveOrZero
    private double hotWater;

    @PositiveOrZero
    private double gas;

    @NotNull
    private LocalDateTime timestamp;


}
