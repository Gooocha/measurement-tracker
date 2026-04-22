package org.example.measurementtracker1.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TesseractOcrService implements OcrService {

    private static final String TESSERACT_COMMAND = "/opt/homebrew/bin/tesseract";

    @Override
    public String extractText(File file) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    TESSERACT_COMMAND,
                    file.getAbsolutePath(),
                    "stdout",
                    "-l", "eng"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Tesseract process failed with code: " + exitCode);
            }

            return output.toString().trim();

        } catch (Exception e) {
            throw new RuntimeException("Failed to perform OCR via tesseract CLI", e);
        }
    }

    @Override
    public Double extractValue(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("OCR text is empty");
        }

        String normalized = text.replace(",", ".").replaceAll("[^0-9.]", " ");

        Pattern pattern = Pattern.compile("\\d+(?:\\.\\d+)?");
        Matcher matcher = pattern.matcher(normalized);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }

        throw new IllegalArgumentException("Could not extract numeric value from OCR text: " + text);
    }
}