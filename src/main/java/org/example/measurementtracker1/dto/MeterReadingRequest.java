package org.example.measurementtracker1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.measurementtracker1.model.ReadingSource;
import org.example.measurementtracker1.model.ReadingType;

import java.time.LocalDateTime;

@Data
public class MeterReadingRequest {

    @NotNull
    private Long userId;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private Double value;

    @NotNull
    private ReadingType type;

    @NotNull
    private ReadingSource source;
}