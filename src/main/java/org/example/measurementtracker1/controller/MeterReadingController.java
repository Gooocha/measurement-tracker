package org.example.measurementtracker1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.dto.MeterReadingRequest;
import org.example.measurementtracker1.model.MeterReading;
import org.example.measurementtracker1.service.MeterReadingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/readings")
@RequiredArgsConstructor
public class MeterReadingController {

    private final MeterReadingService meterReadingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeterReading saveReading(@Valid @RequestBody MeterReadingRequest request) {
        return meterReadingService.saveReading(request);
    }

    @GetMapping("/{userId}")
    public List<MeterReading> getReadingsByUserId(@PathVariable Long userId) {
        return meterReadingService.getReadingsByUserId(userId);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public MeterReading uploadReadingImage(@RequestParam Long userId,
                                           @RequestParam String timestamp,
                                           @RequestParam String type,
                                           @RequestPart("file") MultipartFile file) {
        return meterReadingService.uploadReadingImage(userId, timestamp, type, file);
    }
}