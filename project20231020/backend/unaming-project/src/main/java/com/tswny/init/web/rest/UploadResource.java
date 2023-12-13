package com.tswny.init.web.rest;

import com.tswny.init.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadResource {
    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    private final FileUploadService fileUploadService;

    public UploadResource(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/common")
    public ResponseEntity<String> upload(@RequestParam(value = "file") MultipartFile file) {
        String path = fileUploadService.upload(file);
        return ResponseEntity.ok(path);
    }
}
