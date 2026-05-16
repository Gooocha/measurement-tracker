package org.example.measurementtracker1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.regex.*;

@Service
public class TesseractOcrService implements OcrService {
    @Value("${app.tesseract.command:tesseract}") private String cmd;
    @Override
    public String extractText(File file) {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd, file.getAbsolutePath(), "stdout", "-l", "eng", "--psm", "6");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (p.waitFor()!=0) throw new RuntimeException("OCR processing error");
            return out.trim();
        } catch (Exception e) { throw new RuntimeException("OCR processing error", e); }
    }
    @Override public Double extractValue(String text) {
        Matcher m = Pattern.compile("(?<!\\d)(\\d{3,8}(?:[.,]\\d{1,3})?)(?!\\d)").matcher(text==null?"":text);
        Double best = null;
        while(m.find()){ double v = Double.parseDouble(m.group(1).replace(',','.')); if(best==null || v>best) best=v; }
        if(best==null) throw new IllegalArgumentException("Could not extract value");
        return best;
    }
}
