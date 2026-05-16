package org.example.measurementtracker1.dto.reading;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ManualReadingRequest { @NotNull Long meterId; @NotNull LocalDateTime timestamp; @NotNull @PositiveOrZero Double value; }
