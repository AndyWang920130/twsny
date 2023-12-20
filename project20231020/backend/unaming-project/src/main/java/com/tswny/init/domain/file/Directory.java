package com.tswny.init.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tswny.init.domain.AbstractAuditingEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Directory extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "directory")
    private List<File> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "children", allowSetters = true)
    private Directory parent;

    @OneToMany(mappedBy = "parent")
    private Set<Directory> children = new HashSet<>();

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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public Set<Directory> getChildren() {
        return children;
    }

    public void setChildren(Set<Directory> children) {
        this.children = children;
    }
}
