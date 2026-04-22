package org.example.measurementtracker1.repository;

import org.example.measurementtracker1.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    List<MeterReading> findByUserId(Long userId);
}