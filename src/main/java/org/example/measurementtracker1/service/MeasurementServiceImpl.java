package org.example.measurementtracker1.service;

import org.example.measurementtracker1.dto.MeasurementRequest;
import org.example.measurementtracker1.model.Measurement;
import org.example.measurementtracker1.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository repository;

    public MeasurementServiceImpl(MeasurementRepository repository) {
        this.repository = repository;
    }


    @Override
    public void saveMeasurement(MeasurementRequest request) {
        if (request.getHotWater() < request.getColdWater()) {
            log.warn("Suspicious data: hot water < cold water");
            throw new IllegalArgumentException("Hot water usage cannot be less than cold water");
        }

        if (isDuplicate(request)) {
            log.info("Duplicate measurement for userId={} at timestamp={} was ignored",
                    request.getUserId(), request.getTimestamp());
            return;
        }

        Measurement measurement = new Measurement();

        measurement.setUserId(request.getUserId());
        measurement.setTimestamp(request.getTimestamp());
        measurement.setGas(request.getGas());
        measurement.setHotWater(request.getHotWater());
        measurement.setColdWater(request.getColdWater());

        try {
            repository.save(measurement);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.info("Duplicate measurement for userId={} at timestamp={} was ignored due to race condition", request.getUserId(), request.getTimestamp());
        }


    }

    private boolean isDuplicate(MeasurementRequest request) {
        return repository.existsByUserIdAndTimestamp(request.getUserId(), request.getTimestamp());
    }

    @Override
    public List<Measurement> getMeasurementsByUserId(long userId) {

        return repository.findByUserId(userId);
    }



}
