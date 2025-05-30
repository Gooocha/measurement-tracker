package org.example.measurementtracker1.service;


import org.example.measurementtracker1.dto.MeasurementRequest;
import org.example.measurementtracker1.model.Measurement;
import org.example.measurementtracker1.repository.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceTest {
    @Mock
    private MeasurementRepository measurementRepository;


    @InjectMocks
    private MeasurementServiceImpl measurementService;


    @Test
    void saveMeasurement_shouldNotSaveDuplicate_test(){
        MeasurementRequest measurementRequest = new MeasurementRequest();

        measurementRequest.setUserId(1L);
        measurementRequest.setTimestamp(LocalDateTime.of(2025, 5, 28, 10, 0));

        when(measurementRepository.existsByUserIdAndTimestamp(
                measurementRequest.getUserId(), measurementRequest.getTimestamp())).thenReturn(true);

        measurementService.saveMeasurement(measurementRequest);

        verify(measurementRepository,never()).save(any());
        }
    @Test
    void saveMeasurement_shouldSaveMeasurement_whenNotDuplicate(){

        MeasurementRequest measurementRequest = new MeasurementRequest();


        measurementRequest.setUserId(1L);

        measurementRequest.setTimestamp(LocalDateTime.of(2025, 5, 28, 10, 0));

        measurementRequest.setGas(12.5);

        measurementRequest.setColdWater(12.0);

        measurementRequest.setHotWater(14.2);

        when(measurementRepository.existsByUserIdAndTimestamp(
                measurementRequest.getUserId(),
                measurementRequest.getTimestamp())).thenReturn(false);


        measurementService.saveMeasurement(measurementRequest);

        ArgumentCaptor<Measurement> measurementArgumentCaptor = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementRepository).save(measurementArgumentCaptor.capture());
        Measurement saved = measurementArgumentCaptor.getValue();

        assertEquals(measurementRequest.getUserId(), saved.getUserId());
        assertEquals(measurementRequest.getTimestamp(), saved.getTimestamp());
        assertEquals(measurementRequest.getGas(), saved.getGas());
        assertEquals(measurementRequest.getColdWater(), saved.getColdWater());
        assertEquals(measurementRequest.getHotWater(), saved.getHotWater());


        }
    @Test
    void saveMeasurement_shouldSaveCorrectFields() {
        MeasurementRequest measurementRequest = new MeasurementRequest();
        measurementRequest.setUserId(1L);
        measurementRequest.setTimestamp(LocalDateTime.of(2025, 5, 28, 10, 0));
        measurementRequest.setGas(12.5);
        measurementRequest.setColdWater(12.0);
        measurementRequest.setHotWater(14.2);

        when(measurementRepository.existsByUserIdAndTimestamp(
                measurementRequest.getUserId(),
                measurementRequest.getTimestamp()
        )).thenReturn(false);

        measurementService.saveMeasurement(measurementRequest);


        ArgumentCaptor<Measurement> measurementArgumentCaptor = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementRepository).save(measurementArgumentCaptor.capture());
        Measurement saved = measurementArgumentCaptor.getValue();
        assertEquals(measurementRequest.getUserId(), saved.getUserId(), "user id is incorrect");
        assertEquals(measurementRequest.getTimestamp(), saved.getTimestamp(), "timestamp is incorrect");
        assertEquals(measurementRequest.getGas(), saved.getGas(), "gas is incorrect");
        assertEquals(measurementRequest.getColdWater(), saved.getColdWater(),"cold water is incorrect");
        assertEquals(measurementRequest.getHotWater(), saved.getHotWater(),"hotwater is incorrect");

    }

}

