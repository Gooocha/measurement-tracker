package org.example.measurementtracker1.repository;

import org.example.measurementtracker1.model.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterRepository extends JpaRepository<Meter, Long> {
    Optional<Meter> findBySerialNumber(String serialNumber);
    List<Meter> findByUserId(Long userId);
}