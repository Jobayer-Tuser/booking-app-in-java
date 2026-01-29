package org.booking.misc;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/multitask")
public class FileUploadController {

    private final LocalImageStorageService storageService;

    public FileUploadController(LocalImageStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/file-upload")
    public ImageMetaData restApiFileUploader(@RequestParam("image") MultipartFile imageFile) throws IOException {
        return storageService.uploadImageFile(imageFile);
    }

    @GetMapping("/view-image/{file_name}")
    public ResponseEntity<Resource> restApiFileViewer(@PathVariable("file_name") String fileName) throws IOException {
        Resource imageResource = storageService.getFileResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=image-name")
                .body(imageResource);
    }
}
