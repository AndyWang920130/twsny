package com.tswny.init.domain.clothes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tswny.init.domain.AbstractAuditingEntity;
import com.tswny.init.domain.enumeration.ClothesTypeEnum;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Clothes extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "clothes", allowSetters = true)
    private Brand brand;

    private Double price;

    private Instant purchaseDate;

    @Enumerated(EnumType.STRING)
    private ClothesTypeEnum clothesTypeEnum;

    // 多个图片则已分号隔开
    private String imagePaths;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public ClothesTypeEnum getClothesTypeEnum() {
        return clothesTypeEnum;
    }

    public void setClothesTypeEnum(ClothesTypeEnum clothesTypeEnum) {
        this.clothesTypeEnum = clothesTypeEnum;
    }

    public String getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String imagePaths) {
        this.imagePaths = imagePaths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
