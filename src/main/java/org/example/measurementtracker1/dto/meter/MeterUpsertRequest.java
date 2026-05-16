package org.example.measurementtracker1.dto.meter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.measurementtracker1.model.MeterType;

@Data
public class MeterUpsertRequest { @NotBlank String serialNumber; @NotNull MeterType type; String title; }
