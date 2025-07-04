package org.example.measurementtracker1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.example.measurementtracker1.dto.MeasurementRequest;
import org.example.measurementtracker1.model.Measurement;
import org.example.measurementtracker1.service.MeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/measurements")
@Validated
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }
    @GetMapping("/{userId}/filter")
    @Operation(summary = "Получить измерения по дате", description = "Фильтрация по userId и диапазону дат")
    public ResponseEntity<Page<Measurement>> getFilteredMeasurements(

            @PathVariable long userId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return ResponseEntity.ok(measurementService.getMeasurementsByUserIdAndDateRange(userId, start, end, pageable));
    }


    @Operation(
            summary = "Создать новое измерение",
            description = "Сохраняет новое измерение для указанного пользователя. Возвращает статус 201, если значение уникально."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Измерение успешно сохранено"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса")
    })


    @PostMapping
    public ResponseEntity<Void> createMeasurement(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные измерения", required = true)
            @Valid @RequestBody MeasurementRequest request) {
        measurementService.saveMeasurement(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "получить измерения по userId", description = "возвращает список всех измерений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })



    @GetMapping("/{userId}")
    public ResponseEntity<List<Measurement>> getMeasurements(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable long userId) {

        return ResponseEntity.ok(measurementService.getMeasurementsByUserId(userId));
    }

}
