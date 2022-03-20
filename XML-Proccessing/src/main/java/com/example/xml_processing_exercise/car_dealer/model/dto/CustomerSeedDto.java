package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedDto {

    @XmlAttribute
    private String name;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;

    public CustomerSeedDto() {
    }

    public String getName() {
        return name;
    }

    public CustomerSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public CustomerSeedDto setBirthDate(String bithtDate) {
        this.birthDate = bithtDate;
        return this;
    }

    public Boolean isYoungDriver() {
        return isYoungDriver;
    }

    public CustomerSeedDto setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
        return this;
    }
}
