package org.example.measurementtracker1.service;

import org.example.measurementtracker1.dto.MeasurementRequest;
import org.example.measurementtracker1.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementService {

    void saveMeasurement(MeasurementRequest request);

    List<Measurement> getMeasurementsByUserId(long userId);

    Page<Measurement> getMeasurementsByUserIdAndDateRange(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
