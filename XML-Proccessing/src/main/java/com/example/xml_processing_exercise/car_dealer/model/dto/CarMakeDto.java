package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarMakeDto {
    @XmlAttribute
    private Long id;
    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private BigDecimal travelledDistance;

    public CarMakeDto() {
    }

    public Long getId() {
        return id;
    }

    public CarMakeDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMake() {
        return make;
    }

    public CarMakeDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarMakeDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigDecimal getTravelledDistance() {
        return travelledDistance;
    }

    public CarMakeDto setTravelledDistance(BigDecimal travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }
}
