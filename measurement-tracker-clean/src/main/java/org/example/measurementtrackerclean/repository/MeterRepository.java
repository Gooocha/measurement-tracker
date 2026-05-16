package org.example.measurementtrackerclean.repository;
import org.example.measurementtrackerclean.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface MeterRepository extends JpaRepository<Meter,Long>{List<Meter> findByUserAndActiveTrue(User user); Optional<Meter> findByIdAndUser(Long id, User user); boolean existsBySerialNumber(String serialNumber);} 
