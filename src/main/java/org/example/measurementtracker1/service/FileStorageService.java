package org.example.measurementtracker1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class FileStorageService {
    @Value("${app.upload.dir:uploads}") private String uploadDir;
    @Value("${app.upload.max-file-size-bytes:5242880}") private long maxSize;
    @Value("${app.upload.allowed-content-types:image/png,image/jpeg,image/jpg}") private String allowed;

    public String saveOriginal(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("file not empty required");
        if (file.getSize() > maxSize) throw new IllegalArgumentException("file too large");
        Set<String> allowedSet = Set.of(allowed.split(","));
        if (!allowedSet.contains(Objects.requireNonNullElse(file.getContentType(), ""))) throw new IllegalArgumentException("unsupported file type");
        try {
            Path dir = Paths.get(uploadDir, "original").normalize(); Files.createDirectories(dir);
            String ext = Optional.ofNullable(file.getOriginalFilename()).filter(n->n.contains(".")).map(n->n.substring(n.lastIndexOf('.'))).orElse(".bin");
            Path out = dir.resolve(UUID.randomUUID()+ext).normalize();
            if(!out.startsWith(dir)) throw new IllegalArgumentException("path traversal detected");
            Files.copy(file.getInputStream(), out, StandardCopyOption.REPLACE_EXISTING);
            return out.toString();
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}
