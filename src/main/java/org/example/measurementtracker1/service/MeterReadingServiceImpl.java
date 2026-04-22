package org.example.measurementtracker1.service;

import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.dto.MeterReadingRequest;
import org.example.measurementtracker1.model.MeterReading;
import org.example.measurementtracker1.model.ReadingSource;
import org.example.measurementtracker1.model.ReadingType;
import org.example.measurementtracker1.repository.MeterReadingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;
    private final FileStorageService fileStorageService;
    private final OcrService ocrService;

    @Override
    public MeterReading saveReading(MeterReadingRequest request) {
        MeterReading meterReading = new MeterReading();
        meterReading.setUserId(request.getUserId());
        meterReading.setTimestamp(request.getTimestamp());
        meterReading.setValue(request.getValue());
        meterReading.setType(request.getType());
        meterReading.setSource(request.getSource());

        return meterReadingRepository.save(meterReading);
    }

    @Override
    public List<MeterReading> getReadingsByUserId(Long userId) {
        return meterReadingRepository.findByUserId(userId);
    }

    @Override
    public MeterReading uploadReadingImage(Long userId, String timestamp, String type, MultipartFile file) {
        String imagePath = fileStorageService.saveFile(file);

        File savedFile = new File(imagePath);
        String rawText = ocrService.extractText(savedFile);
        Double extractedValue = ocrService.extractValue(rawText);

        MeterReading meterReading = new MeterReading();
        meterReading.setUserId(userId);
        meterReading.setTimestamp(LocalDateTime.parse(timestamp));
        meterReading.setType(ReadingType.valueOf(type));
        meterReading.setSource(ReadingSource.OCR);
        meterReading.setImagePath(imagePath);
        meterReading.setValue(extractedValue);
        meterReading.setOcrRawText(rawText);

        return meterReadingRepository.save(meterReading);
    }
}