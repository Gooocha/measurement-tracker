package org.example.measurementtracker1.model.ocr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.measurementtracker1.model.Meter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocr_tasks")
@Getter
@Setter
public class OcrTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OcrTaskStatus status = OcrTaskStatus.NEW;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "original_path", nullable = false)
    private String originalPath;

    @Column(name = "processed_path")
    private String processedPath;

    @Column(name = "extracted_text", length = 2000)
    private String extractedText;

    @Column(name = "extracted_value")
    private Double extractedValue;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @PrePersist
    void prePersist() {createdAt = LocalDateTime.now();}
}
