package org.example.measurementtracker1.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.model.*;
import org.example.measurementtracker1.model.ocr.*;
import org.example.measurementtracker1.repository.*;
import org.example.measurementtracker1.repository.ocr.OcrTaskRepository;
import org.example.measurementtracker1.service.FileStorageService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RestController("apiOcrTaskController") @RequestMapping("/ocr/tasks") @RequiredArgsConstructor
public class OcrTaskController {
  private final OcrTaskRepository repo; private final MeterRepository meterRepo; private final UserRepository userRepo; private final FileStorageService fs;
  private User u(Principal p){ return userRepo.findByUsername(p.getName()).orElseThrow(); }
  @PostMapping @ResponseStatus(HttpStatus.ACCEPTED)
  public Map<String,Object> create(Principal p,@RequestParam Long meterId,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp,@RequestPart MultipartFile file){
      Meter meter = meterRepo.findByIdAndUser(meterId,u(p)).orElseThrow(()->new IllegalArgumentException("meter not found"));
      OcrTask t = new OcrTask(); t.setMeter(meter); t.setTimestamp(timestamp); t.setOriginalPath(fs.saveOriginal(file));
      repo.save(t); return Map.of("taskId",t.getId(),"status",t.getStatus());
  }
  @GetMapping("/{id}") public OcrTask one(Principal p,@PathVariable Long id){ return repo.findByIdAndMeterUser(id,u(p)).orElseThrow(()->new IllegalArgumentException("task not found")); }
  @GetMapping public List<OcrTask> all(Principal p){ return repo.findByMeterUserOrderByCreatedAtDesc(u(p)); }
}
