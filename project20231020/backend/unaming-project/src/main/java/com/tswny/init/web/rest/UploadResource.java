package com.tswny.init.web.rest;

import com.tswny.init.service.WebsiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadResource {
    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    @PostMapping("/common")
    public ResponseEntity<Void> upload(@RequestParam(value = "file") MultipartFile file) {
        log.info("multipartFile name: {}", file.getName());
        return ResponseEntity.noContent().build();
    }
}
