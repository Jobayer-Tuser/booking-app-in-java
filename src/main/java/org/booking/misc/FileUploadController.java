package org.booking.misc;

import jakarta.validation.Valid;
import org.booking.Validations.File;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/multitask")
public class FileUploadController {

    private final LocalImageStorageService storageService;

    public FileUploadController(LocalImageStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/sinlge-file-upload")
    public ImageMetaData restApiFileUploader(@RequestParam("image") MultipartFile imageFile) throws IOException {
        return storageService.uploadImageFile(imageFile);
    }

    @PostMapping(value = "/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ImageMetaData> multipleFileUploader(@RequestParam("images") List<MultipartFile> imageFile)
            throws IOException {
        return storageService.uploadMultipleFiles(imageFile);
    }

    @GetMapping("/view-image/{file_name}")
    public ResponseEntity<Resource> restApiFileViewer(@PathVariable("file_name") String fileName) throws IOException {
        Resource imageResource = storageService.getFileResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=image-name")
                .body(imageResource);
    }
}
