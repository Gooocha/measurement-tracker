package org.example.measurementtrackerclean.model;
import jakarta.persistence.*;import lombok.Getter;import lombok.Setter;import java.time.LocalDateTime;
@Entity @Table(name="ocr_tasks") @Getter @Setter
public class OcrTask {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="meter_id",nullable=false) private Meter meter;
 @Column(nullable=false) private String imagePath;
 @Column(nullable=false) private LocalDateTime timestamp;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private OcrTaskStatus status=OcrTaskStatus.NEW;
 @Column(nullable=false) private LocalDateTime createdAt;
 private LocalDateTime processedAt;
 @Column(length=2000) private String ocrRawText;
 private Double extractedValue;
 @Column(length=1000) private String errorMessage;
 @PrePersist void pre(){createdAt=LocalDateTime.now();}
}
