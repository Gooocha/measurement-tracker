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

        if (repository.existsByUserIdAndTimestamp(request.getUserId(), request.getTimestamp())) {
            if (repository.existsByUserIdAndTimestamp(request.getUserId(), request.getTimestamp())) {
                log.info("Duplicate measurement for userId={} at timestamp={} was ignored",
                        request.getUserId(), request.getTimestamp());
                return;
            }

            return;
            // TODO: вынести проверку на дубликат в отдельный метод для читаемости

        }

        Measurement measurement = new Measurement();

        measurement.setUserId(request.getUserId());
        measurement.setTimestamp(request.getTimestamp());
        measurement.setGas(request.getGas());
        measurement.setHotWater(request.getHotWater());
        measurement.setColdWater(request.getColdWater());

        repository.save(measurement);
        // TODO: добавить логирование ошибок в случае некорректного сохранения

    }

    @Override
    public List<Measurement> getMeasurementsByUserId(long userId) {

        return repository.findByUserId(userId);
    }



}
