package org.example.measurementtracker1.controller;

import jakarta.validation.Valid;
import org.example.measurementtracker1.dto.MeasurementRequest;
import org.example.measurementtracker1.model.Measurement;
import org.example.measurementtracker1.service.MeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/measurements")
@Validated
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping
    public ResponseEntity<Void> createMeasurement(@Valid @RequestBody MeasurementRequest request) {
        measurementService.saveMeasurement(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{userId}")
   public ResponseEntity<List<Measurement>> getMeasurement(@PathVariable long userId) {
        return ResponseEntity.ok(measurementService.getMeasurementsByUserId(userId));
    }
    // TODO: добавить фильтрацию истории по диапазону дат

}
