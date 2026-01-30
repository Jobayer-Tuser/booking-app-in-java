package org.booking.misc;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

        return new ImageMetaData(
            storeMediaFile(file),
            file.getName(),
            file.getOriginalFilename(),
            file.getContentType(), file.getSize()
        );
    }

    public List<ImageMetaData> uploadMultipleFiles(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    try {
                        return new ImageMetaData(
                                storeMediaFile(file),
                                file.getName(),
                                file.getOriginalFilename(),
                                file.getContentType(), file.getSize()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    private String storeMediaFile(MultipartFile file) throws IOException {

        Path targetPath = generatePath();
        Files.createDirectories(targetPath);

        String extension    = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String storedName   = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
        Path finalDes       = targetPath.resolve(storedName);

        file.transferTo(finalDes);
        return rootPath.relativize(finalDes).toString();
    }


    public Resource getFileResource(String storedFileNameOrPath) throws IOException {
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

    private Path generatePath() {
        String dateDirectory = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return rootPath.resolve(dateDirectory);
    }

    private BufferedImage resizeImage(BufferedImage sourceImage, int maxPixelSize, int targetHeight, int targetWidth) {

        Image resultingImage = sourceImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);


        BufferedImage resizedImage = new BufferedImage(targetHeight, targetWidth, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = resizedImage.createGraphics();
        imageGraphics.drawImage(resultingImage, 0, 0, null);
        imageGraphics.dispose();

        // another example to be like ( Graphics 2D Class )
        BufferedImage resizedImage2 = new BufferedImage(targetHeight, targetWidth, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics2 = resizedImage.createGraphics();
        imageGraphics.drawImage(sourceImage, 0,0, targetHeight, targetWidth, null);

        return resizedImage;
    }

}
