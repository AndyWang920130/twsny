package com.tswny.init.service.dto;

import com.tswny.init.domain.file.Directory;
import com.tswny.init.domain.file.File;
import com.tswny.init.service.dto.enumeration.FileManagementType;
import org.springframework.data.domain.Sort;

import java.time.Instant;

public class FileManagementDTO {
    private Long id;
    private String name;
    private Instant updateDate;
    private String size;
    private FileManagementType fileManagementType;

    public FileManagementDTO() {}

    public FileManagementDTO(Directory directory) {
        this.id = directory.getId();
        this.name = directory.getName();
        this.updateDate = directory.getLastModifiedDate();
        this.size = "unknown";
        this.fileManagementType = FileManagementType.FOLDER;
    }

    public FileManagementDTO(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.updateDate = file.getLastModifiedDate();
        this.size = "unknown";
        this.fileManagementType = FileManagementType.FILE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public FileManagementType getFileManagementType() {
        return fileManagementType;
    }

    public void setFileManagementType(FileManagementType fileManagementTyp) {
        this.fileManagementType = fileManagementTyp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
