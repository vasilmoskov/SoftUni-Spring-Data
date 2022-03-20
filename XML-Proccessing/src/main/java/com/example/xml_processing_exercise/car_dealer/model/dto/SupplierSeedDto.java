package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedDto {
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "is-importer")
    private Boolean isImporter;

    public SupplierSeedDto() {
    }

    public String getName() {
        return name;
    }

    public SupplierSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean isImporter() {
        return isImporter;
    }

    public SupplierSeedDto setImporter(Boolean importer) {
        isImporter = importer;
        return this;
    }
}
