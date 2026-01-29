package org.booking.misc;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class LocalImageStorageService {

    private final Path rootPath;
    private final ImageStorageProperties storageProperties;

    public LocalImageStorageService(ImageStorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.rootPath = Paths.get(storageProperties.getBasePath());
    }

    public ImageMetaData uploadImageFile(MultipartFile file) throws IOException{
        validateImage(file);
        String storagePath;
        try(InputStream inputStream = file.getInputStream()) {
            storagePath = storeMediaFile(inputStream, file.getOriginalFilename());
        }

        String originalName = file.getOriginalFilename();
        String fileName     = file.getName();
        String fileType     = file.getContentType();
        Long fileSize       = file.getSize();
        byte[] fileBytes    = file.getBytes();

        return new ImageMetaData(storagePath, fileName, originalName, fileType, fileSize, fileBytes);
    }

    private String storeMediaFile(InputStream inputStream, String originalName) throws IOException {
        LocalDate today = LocalDate.now();
        Path dateDirectory = rootPath.resolve(
                today.getYear() + File.separator +
                        String.format("%02d", today.getMonthValue()) + File.separator +
                        String.format("%02d", today.getDayOfMonth()));

        Files.createDirectories(dateDirectory);

        String extension    = getFileExtension(originalName);
        String storedName   = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
        Path filePath       = dateDirectory.resolve(storedName);

        try (OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)) {
            StreamUtils.copy(inputStream, outputStream);
        }

        return rootPath.relativize(filePath).toString();
    }

    private Resource getFileResource(String storedFileNameOrPath) throws IOException {
        Path filePath = rootPath.resolve(storedFileNameOrPath).normalize().toAbsolutePath();
        Path normalizedRoot = rootPath.normalize().toAbsolutePath();

        if (!filePath.startsWith(normalizedRoot)) {
            throw new SecurityException("Access Denied");
        }

        if (! Files.exists(filePath)) {
            throw new FileNotFoundException("File Not found!");
        }

        return new UrlResource(filePath.toUri());
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf(".");
        return lastDot == -1 ? "" : fileName.substring(lastDot + 1);
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty())
            throw new IllegalArgumentException("File is empty!");

        String mimeType = file.getContentType();

        if (mimeType == null || !storageProperties.getAllowedMimeTypes().contains(mimeType))
            throw new IllegalArgumentException("Invalid mime type!");
    }
}
