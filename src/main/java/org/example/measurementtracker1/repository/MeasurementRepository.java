package org.example.measurementtracker1.repository;


import org.example.measurementtracker1.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByUserId(long userId);
    boolean existsByUserIdAndTimestamp(long userId, LocalDateTime timestamp);
    List<Measurement> findByUserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end);
    Page<Measurement> findByUserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
