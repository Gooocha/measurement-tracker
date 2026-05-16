package org.example.measurementtracker1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "meter_readings", uniqueConstraints = @UniqueConstraint(columnNames = {"meter_id", "timestamp"}))
@Getter
@Setter
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingSource source;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "ocr_raw_text", length = 2000)
    private String ocrRawText;
}
