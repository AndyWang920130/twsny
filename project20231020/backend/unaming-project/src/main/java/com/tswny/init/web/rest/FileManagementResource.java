package com.tswny.init.web.rest;

import com.tswny.init.domain.file.File;
import com.tswny.init.service.FileManagementService;
import com.tswny.init.service.dto.FileManagementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fileManagement")
public class FileManagementResource {
    private final FileManagementService fileManagementService;


    public FileManagementResource(FileManagementService fileManagementService) {
        this.fileManagementService = fileManagementService;
    }

    @GetMapping
    public ResponseEntity<Page<FileManagementDTO>> queryByPage(@RequestParam(required = false) String keyword,
                                                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.fileManagementService.queryByPage(keyword, pageable));
    }
}
