package org.booking.misc;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/multitask")
public class FileUploadController {

    private final LocalImageStorageService localImageStorageService;

    public FileUploadController(LocalImageStorageService localImageStorageService) {
        this.localImageStorageService = localImageStorageService;
    }

    @PostMapping("/file-upload")
    public ImageMetaData restApiFileUploader(@RequestParam("image") MultipartFile imageFile) throws IOException {
        return localImageStorageService.uploadImageFile(imageFile);
    }

    @GetMapping("/view-image/{file_name}")
    public byte[] restApiFileViewer(@PathVariable("file_name") String fileName) throws IOException {
        String filePath = "/Users/jobayer/Maven/booking/src/main/resources/files/" + fileName;
        return Files.readAllBytes(new File(filePath).toPath());
    }
}
