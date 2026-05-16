package org.example.measurementtracker1.repository;

import org.example.measurementtracker1.model.Meter;
import org.example.measurementtracker1.model.MeterReading;
import org.example.measurementtracker1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {
    List<MeterReading> findByMeterUserOrderByTimestampDesc(User user);
    List<MeterReading> findByMeterAndMeterUserOrderByTimestampDesc(Meter meter, User user);
    Optional<MeterReading> findByIdAndMeterUser(Long id, User user);

    @Query("select mr from MeterReading mr where mr.meter.user = :user and mr.timestamp between :from and :to order by mr.timestamp desc")
    List<MeterReading> findByUserBetween(User user, LocalDateTime from, LocalDateTime to);
}
