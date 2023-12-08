package com.tswny.init.domain.clothes;

import com.tswny.init.domain.AbstractAuditingEntity;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Brand extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String country;

    private String originator;

    private Instant originalDate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public Instant getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(Instant originalDate) {
        this.originalDate = originalDate;
    }
}
