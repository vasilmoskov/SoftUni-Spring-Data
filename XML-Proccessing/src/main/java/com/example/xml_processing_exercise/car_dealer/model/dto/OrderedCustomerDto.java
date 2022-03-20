package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderedCustomerDto {
    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;

    public OrderedCustomerDto() {
    }

    public Long getId() {
        return id;
    }

    public OrderedCustomerDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderedCustomerDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public OrderedCustomerDto setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public OrderedCustomerDto setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
        return this;
    }
}
