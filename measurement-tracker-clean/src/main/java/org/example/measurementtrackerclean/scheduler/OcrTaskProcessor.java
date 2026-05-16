package org.example.measurementtrackerclean.scheduler;
import lombok.RequiredArgsConstructor;import org.example.measurementtrackerclean.service.CoreServices;import org.springframework.scheduling.annotation.Scheduled;import org.springframework.stereotype.Component;
@Component @RequiredArgsConstructor public class OcrTaskProcessor { private final CoreServices s; @Scheduled(fixedDelayString="${app.ocr.scheduler-delay-ms:3000}") public void run(){ s.processNextTask(); } }
