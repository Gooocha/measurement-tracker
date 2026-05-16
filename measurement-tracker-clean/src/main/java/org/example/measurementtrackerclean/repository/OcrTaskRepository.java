package org.example.measurementtrackerclean.repository;
import org.example.measurementtrackerclean.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface OcrTaskRepository extends JpaRepository<OcrTask,Long>{
 Optional<OcrTask> findFirstByStatusOrderByCreatedAtAsc(OcrTaskStatus status);
 List<OcrTask> findByMeterUserOrderByCreatedAtDesc(User user);
 Optional<OcrTask> findByIdAndMeterUser(Long id, User user);
}
