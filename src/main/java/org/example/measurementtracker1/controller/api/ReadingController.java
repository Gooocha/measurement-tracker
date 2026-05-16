package org.example.measurementtracker1.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.dto.reading.ManualReadingRequest;
import org.example.measurementtracker1.model.*;
import org.example.measurementtracker1.repository.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController("apiReadingController") @RequestMapping("/readings") @RequiredArgsConstructor
public class ReadingController {
  private final MeterRepository meterRepository; private final MeterReadingRepository readingRepository; private final UserRepository userRepository;
  private User u(Principal p){ return userRepository.findByUsername(p.getName()).orElseThrow(); }
  @PostMapping("/manual") public MeterReading create(Principal p, @Valid @RequestBody ManualReadingRequest r){ Meter m=meterRepository.findByIdAndUser(r.getMeterId(),u(p)).orElseThrow(()->new IllegalArgumentException("meter not found")); MeterReading mr=new MeterReading(); mr.setMeter(m); mr.setTimestamp(r.getTimestamp()); mr.setValue(r.getValue()); mr.setSource(ReadingSource.MANUAL); return readingRepository.save(mr);}  
  @GetMapping public List<MeterReading> list(Principal p){ return readingRepository.findByMeterUserOrderByTimestampDesc(u(p)); }
}
