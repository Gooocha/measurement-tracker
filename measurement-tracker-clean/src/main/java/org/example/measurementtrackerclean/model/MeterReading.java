package org.example.measurementtrackerclean.model;
import jakarta.persistence.*;import lombok.Getter;import lombok.Setter;import java.time.LocalDateTime;
@Entity @Table(name="meter_readings",uniqueConstraints=@UniqueConstraint(columnNames={"meter_id","timestamp"})) @Getter @Setter
public class MeterReading {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="meter_id",nullable=false) private Meter meter;
 @Column(nullable=false) private LocalDateTime timestamp;
 @Column(nullable=false) private Double value;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private ReadingSource source;
 private String imagePath;
 @Column(length=2000) private String ocrRawText;
 @Column(nullable=false) private LocalDateTime createdAt;
 @PrePersist void pre(){createdAt=LocalDateTime.now();}
}
