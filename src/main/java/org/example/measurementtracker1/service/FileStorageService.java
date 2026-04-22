package org.example.measurementtracker1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    public String saveFile(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String safeFilename = System.currentTimeMillis() + "_" + originalFilename;

            Path filePath = uploadPath.resolve(safeFilename);
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }
}