package org.example.measurementtracker1.service;

import java.io.File;

public interface OcrService {
    String extractText(File file);
    Double extractValue(String text);
}