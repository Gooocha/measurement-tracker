package org.example.measurementtracker1.repository;

import org.example.measurementtracker1.model.Meter;
import org.example.measurementtracker1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterRepository extends JpaRepository<Meter, Long> {

    // legacy compatibility methods
    Optional<Meter> findBySerialNumber(String serialNumber);
    List<Meter> findByUserId(Long userId);

    List<Meter> findByUserAndActiveTrue(User user);
    Optional<Meter> findByIdAndUser(Long id, User user);
    boolean existsBySerialNumber(String serialNumber);
}