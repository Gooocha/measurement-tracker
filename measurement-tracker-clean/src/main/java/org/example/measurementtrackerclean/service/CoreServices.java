package org.example.measurementtrackerclean.service;

import lombok.RequiredArgsConstructor;
import org.example.measurementtrackerclean.config.JwtService;
import org.example.measurementtrackerclean.dto.*;
import org.example.measurementtrackerclean.model.*;
import org.example.measurementtrackerclean.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;import java.nio.charset.StandardCharsets;import java.nio.file.*;import java.time.LocalDateTime;import java.util.*;import java.util.regex.*;

@Service @RequiredArgsConstructor
public class CoreServices {
  private final UserRepository users; private final MeterRepository meters; private final MeterReadingRepository readings; private final OcrTaskRepository tasks; private final PasswordEncoder pe; private final AuthenticationManager am; private final JwtService jwt;
  @Value("${app.upload.dir:uploads}") String uploadDir; @Value("${app.upload.max-file-size-bytes:5242880}") long max; @Value("${app.upload.allowed-content-types:image/png,image/jpeg,image/jpg}") String allowed; @Value("${app.tesseract.path:tesseract}") String tcmd;

  public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest r){ if(users.existsByUsername(r.username())) throw new IllegalArgumentException("duplicate username"); User u=new User();u.setUsername(r.username());u.setPassword(pe.encode(r.password())); users.save(u); return new AuthDtos.AuthResponse(jwt.generate(u.getUsername())); }
  public AuthDtos.AuthResponse login(AuthDtos.LoginRequest r){ am.authenticate(new UsernamePasswordAuthenticationToken(r.username(),r.password())); return new AuthDtos.AuthResponse(jwt.generate(r.username())); }
  public User me(String username){ return users.findByUsername(username).orElseThrow(); }
  @Transactional public MeterDtos.MeterResponse createMeter(String u, MeterDtos.MeterRequest r){ if(meters.existsBySerialNumber(r.serialNumber())) throw new IllegalArgumentException("duplicate serial number"); Meter m=new Meter(); m.setUser(me(u)); m.setSerialNumber(r.serialNumber()); m.setType(r.type()); m.setTitle(r.title()); meters.save(m); return map(m);} 
  public List<MeterDtos.MeterResponse> meters(String u){ return meters.findByUserAndActiveTrue(me(u)).stream().map(this::map).toList(); }
  public MeterDtos.MeterResponse meter(String u,Long id){ return map(meters.findByIdAndUser(id,me(u)).orElseThrow()); }
  @Transactional public MeterDtos.MeterResponse updateMeter(String u,Long id,MeterDtos.MeterRequest r){ Meter m=meters.findByIdAndUser(id,me(u)).orElseThrow(); m.setType(r.type()); m.setTitle(r.title()); return map(m); }
  @Transactional public void deleteMeter(String u,Long id){ Meter m=meters.findByIdAndUser(id,me(u)).orElseThrow(); m.setActive(false);} 
  @Transactional public ReadingDtos.ReadingResponse manual(String u, ReadingDtos.ManualReadingRequest r){ Meter m=meters.findByIdAndUser(r.meterId(),me(u)).orElseThrow(); MeterReading mr=new MeterReading(); mr.setMeter(m); mr.setTimestamp(r.timestamp()); mr.setValue(r.value()); mr.setSource(ReadingSource.MANUAL); readings.save(mr); return map(mr);} 
  public List<ReadingDtos.ReadingResponse> readings(String u){ return readings.findByMeterUserOrderByTimestampDesc(me(u)).stream().map(this::map).toList(); }
  public ReadingDtos.ReadingResponse reading(String u,Long id){ return map(readings.findByIdAndMeterUser(id,me(u)).orElseThrow()); }
  public List<ReadingDtos.ReadingResponse> byMeter(String u,Long meterId){ Meter m=meters.findByIdAndUser(meterId,me(u)).orElseThrow(); return readings.findByMeterAndMeterUserOrderByTimestampDesc(m,me(u)).stream().map(this::map).toList(); }
  public List<ReadingDtos.ReadingResponse> history(String u,Long meterId,LocalDateTime from,LocalDateTime to){ Meter m=meters.findByIdAndUser(meterId,me(u)).orElseThrow(); return readings.history(m,from,to).stream().map(this::map).toList(); }
  @Transactional public Map<String,Object> queueTask(String u, Long meterId, LocalDateTime ts, MultipartFile file){ Meter m=meters.findByIdAndUser(meterId,me(u)).orElseThrow(); String p=store(file,"original"); OcrTask t=new OcrTask(); t.setMeter(m); t.setTimestamp(ts); t.setImagePath(p); tasks.save(t); return Map.of("taskId",t.getId(),"status",t.getStatus()); }
  public List<OcrTask> taskList(String u){ return tasks.findByMeterUserOrderByCreatedAtDesc(me(u)); }
  public OcrTask task(String u,Long id){ return tasks.findByIdAndMeterUser(id,me(u)).orElseThrow(); }
  @Transactional public void processNextTask(){ tasks.findFirstByStatusOrderByCreatedAtAsc(OcrTaskStatus.NEW).ifPresent(t->{ t.setStatus(OcrTaskStatus.PROCESSING); try{String raw=ocr(new File(t.getImagePath())); Double val=extract(raw); MeterReading mr=new MeterReading(); mr.setMeter(t.getMeter()); mr.setTimestamp(t.getTimestamp()); mr.setValue(val); mr.setSource(ReadingSource.OCR); mr.setImagePath(t.getImagePath()); mr.setOcrRawText(raw); readings.save(mr); t.setOcrRawText(raw); t.setExtractedValue(val); t.setStatus(OcrTaskStatus.DONE);}catch(Exception e){t.setStatus(OcrTaskStatus.FAILED);t.setErrorMessage(e.getMessage());} t.setProcessedAt(LocalDateTime.now());}); }
  private String store(MultipartFile file,String sub){ try{ if(file.isEmpty()) throw new IllegalArgumentException("file empty"); if(file.getSize()>max) throw new IllegalArgumentException("file too large"); var ok=Set.of(allowed.split(",")); if(!ok.contains(Objects.toString(file.getContentType(),""))) throw new IllegalArgumentException("unsupported file type"); Path d=Paths.get(uploadDir,sub).normalize(); Files.createDirectories(d); String ext=Optional.ofNullable(file.getOriginalFilename()).filter(x->x.contains(".")).map(x->x.substring(x.lastIndexOf('.'))).orElse(".bin"); Path out=d.resolve(UUID.randomUUID()+ext).normalize(); if(!out.startsWith(d)) throw new IllegalArgumentException("path traversal"); Files.copy(file.getInputStream(),out,StandardCopyOption.REPLACE_EXISTING); return out.toString(); }catch(IOException e){ throw new RuntimeException(e);} }
  private String ocr(File f) throws Exception { Process p=new ProcessBuilder(tcmd,f.getAbsolutePath(),"stdout","-l","eng").start(); String out=new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8); if(p.waitFor()!=0) throw new RuntimeException("ocr failed"); return out; }
  private Double extract(String raw){ Matcher m=Pattern.compile("(?<!\\d)(\\d{3,8}(?:[.,]\\d{1,2})?)(?!\\d)").matcher(raw==null?"":raw); Double best=null; while(m.find()){double v=Double.parseDouble(m.group(1).replace(',','.')); if(best==null||v>best) best=v;} if(best==null) throw new IllegalArgumentException("value not found"); return best; }
  private MeterDtos.MeterResponse map(Meter m){ return new MeterDtos.MeterResponse(m.getId(),m.getSerialNumber(),m.getType(),m.getTitle(),m.isActive()); }
  private ReadingDtos.ReadingResponse map(MeterReading r){ return new ReadingDtos.ReadingResponse(r.getId(),r.getMeter().getId(),r.getTimestamp(),r.getValue(),r.getSource().name(),r.getImagePath(),r.getOcrRawText()); }
}
