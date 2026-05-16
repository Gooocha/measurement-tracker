package org.example.measurementtracker1.service.ocr;

import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.model.*;
import org.example.measurementtracker1.model.ocr.*;
import org.example.measurementtracker1.repository.MeterReadingRepository;
import org.example.measurementtracker1.repository.ocr.OcrTaskRepository;
import org.example.measurementtracker1.service.OcrService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;

@Service @RequiredArgsConstructor
public class OcrTaskProcessor {
    private final OcrTaskRepository taskRepository; private final OcrService ocrService; private final MeterReadingRepository readingRepository;
    @Scheduled(fixedDelayString = "${app.ocr.poll-ms:3000}") @Transactional
    public void process() {
        taskRepository.findFirstByStatusOrderByCreatedAtAsc(OcrTaskStatus.NEW).ifPresent(task -> {
            task.setStatus(OcrTaskStatus.PROCESSING);
            try {
                String text = ocrService.extractText(new File(task.getOriginalPath()));
                Double value = ocrService.extractValue(text);
                MeterReading r = new MeterReading(); r.setMeter(task.getMeter()); r.setTimestamp(task.getTimestamp()); r.setValue(value); r.setSource(ReadingSource.OCR); r.setImagePath(task.getOriginalPath()); r.setOcrRawText(text);
                readingRepository.save(r);
                task.setExtractedText(text); task.setExtractedValue(value); task.setStatus(OcrTaskStatus.DONE);
            } catch (Exception e) {
                task.setStatus(OcrTaskStatus.FAILED); task.setErrorMessage(e.getMessage());
            }
            task.setProcessedAt(LocalDateTime.now());
        });
    }
}
