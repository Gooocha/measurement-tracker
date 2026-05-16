package org.example.measurementtracker1.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.dto.meter.*;
import org.example.measurementtracker1.model.*;
import org.example.measurementtracker1.repository.*;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController("apiMeterController") @RequestMapping("/meters") @RequiredArgsConstructor
public class MeterController {
    private final MeterRepository meterRepository; private final UserRepository userRepository;
    private User user(Principal p){ return userRepository.findByUsername(p.getName()).orElseThrow(); }
    private MeterDto map(Meter m){ return MeterDto.builder().id(m.getId()).serialNumber(m.getSerialNumber()).type(m.getType()).title(m.getTitle()).active(m.isActive()).build(); }
    @PostMapping public MeterDto create(Principal p,@Valid @RequestBody MeterUpsertRequest r){ if(meterRepository.existsBySerialNumber(r.getSerialNumber())) throw new IllegalArgumentException("duplicate serialNumber"); Meter m=new Meter();m.setSerialNumber(r.getSerialNumber());m.setType(r.getType());m.setTitle(r.getTitle());m.setUser(user(p)); return map(meterRepository.save(m)); }
    @GetMapping public List<MeterDto> list(Principal p){ return meterRepository.findByUserAndActiveTrue(user(p)).stream().map(this::map).toList(); }
    @GetMapping("/{id}") public MeterDto get(Principal p,@PathVariable Long id){ return map(meterRepository.findByIdAndUser(id,user(p)).orElseThrow(()->new IllegalArgumentException("meter not found"))); }
}
