package org.example.measurementtracker1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "meter_readings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "timestamp", "type"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingSource source;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "ocr_raw_text", length = 1000)
    private String ocrRawText;
}