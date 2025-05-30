package org.example.measurementtracker1.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private double hotWater;

    private double coldWater;

    private double gas;

    private LocalDateTime timestamp;


    public Measurement(long userId, LocalDateTime timestamp, double gas, double hotWater, double coldWater) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.gas = gas;
        this.hotWater = hotWater;
        this.coldWater = coldWater;
    }


    public Measurement() {

    }
}
