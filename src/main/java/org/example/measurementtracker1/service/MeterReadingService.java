package org.example.measurementtracker1.service;

import org.example.measurementtracker1.dto.MeterReadingRequest;
import org.example.measurementtracker1.model.MeterReading;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MeterReadingService {

    MeterReading saveReading(MeterReadingRequest request);

    List<MeterReading> getReadingsByUserId(Long userId);

    MeterReading uploadReadingImage(Long userId, String timestamp, String type, MultipartFile file);
}