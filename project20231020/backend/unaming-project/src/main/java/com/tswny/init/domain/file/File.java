package com.tswny.init.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tswny.init.domain.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
public class File extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "files", allowSetters = true)
    private Directory directory;

    private String fileName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
