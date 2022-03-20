package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSupplierDto {
    @XmlAttribute
    private Long id;
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "parts-count")
    private Integer partsCount;

    public LocalSupplierDto() {
    }

    public Long getId() {
        return id;
    }

    public LocalSupplierDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocalSupplierDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public LocalSupplierDto setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
        return this;
    }
}
