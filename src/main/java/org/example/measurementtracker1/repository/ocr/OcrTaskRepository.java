package org.example.measurementtracker1.repository.ocr;

import org.example.measurementtracker1.model.User;
import org.example.measurementtracker1.model.ocr.OcrTask;
import org.example.measurementtracker1.model.ocr.OcrTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OcrTaskRepository extends JpaRepository<OcrTask, Long> {
    Optional<OcrTask> findFirstByStatusOrderByCreatedAtAsc(OcrTaskStatus status);
    Optional<OcrTask> findByIdAndMeterUser(Long id, User user);
    List<OcrTask> findByMeterUserOrderByCreatedAtDesc(User user);
}
