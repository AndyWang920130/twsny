package com.tswny.init.web.rest.vm;

import com.tswny.init.domain.enumeration.ClothesTypeEnum;
import com.tswny.init.service.dto.AbstractAuditingEntityDTO;

import java.time.Instant;

public class ClothesVM extends AbstractAuditingEntityDTO {
    private Long id;
    private String name;
    private Long brandId;
    private ClothesTypeEnum clothesType;
    private Double price;
    private Instant purchaseDate;
    private String[] imagePaths;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public ClothesTypeEnum getClothesType() {
        return clothesType;
    }

    public void setClothesType(ClothesTypeEnum clothesType) {
        this.clothesType = clothesType;
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

    public String[] getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String[] imagePaths) {
        this.imagePaths = imagePaths;
    }
}
