package org.example.measurementtracker1.dto.meter;

import lombok.Builder;
import lombok.Data;
import org.example.measurementtracker1.model.MeterType;

@Data @Builder
public class MeterDto { Long id; String serialNumber; MeterType type; String title; boolean active; }
