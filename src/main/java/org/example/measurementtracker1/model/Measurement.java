package org.example.measurementtracker1.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement",
        indexes = {
                @Index(name = "idx_user_id", columnList = "userId"),
                @Index(name = "idx_user_timestamp", columnList = "userId, timestamp")
        },
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "timestamp"}))
@Getter
@Setter
public class Measurement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Min(0)
    @Column(nullable = false)
    private Double gas;

    @Min(0)
    @Column(nullable = false)
    private Double coldWater;

    @Min(0)
    @Column(nullable = false)
    private Double hotWater;

    public Measurement(long userId, LocalDateTime timestamp,
                       double gas, double hotWater, double coldWater) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.gas = gas;
        this.hotWater = hotWater;
        this.coldWater = coldWater;
    }


    public Measurement() {

    }

}
