package org.example.measurementtracker1.service;

import org.example.measurementtracker1.dto.MeasurementRequest;
import org.example.measurementtracker1.model.Measurement;

import java.util.List;

public interface MeasurementService {
    void saveMeasurement(MeasurementRequest request);
    List<Measurement> getMeasurementsByUserId(long userId);
}
