package org.example.measurementtrackerclean.repository;
import org.example.measurementtrackerclean.model.*;import org.springframework.data.jpa.repository.*;import java.time.LocalDateTime;import java.util.*;
public interface MeterReadingRepository extends JpaRepository<MeterReading,Long>{
 List<MeterReading> findByMeterUserOrderByTimestampDesc(User user);
 List<MeterReading> findByMeterAndMeterUserOrderByTimestampDesc(Meter meter, User user);
 Optional<MeterReading> findByIdAndMeterUser(Long id, User user);
 @Query("select r from MeterReading r where r.meter=:meter and r.timestamp between :from and :to order by r.timestamp desc")
 List<MeterReading> history(Meter meter, LocalDateTime from, LocalDateTime to);
}
