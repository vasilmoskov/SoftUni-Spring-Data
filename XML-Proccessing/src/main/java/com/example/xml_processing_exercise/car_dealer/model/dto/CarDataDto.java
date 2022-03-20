package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarDataDto {
    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private BigDecimal travelledDistance;

    public CarDataDto() {
    }

    public String getMake() {
        return make;
    }

    public CarDataDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarDataDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigDecimal getTravelledDistance() {
        return travelledDistance;
    }

    public CarDataDto setTravelledDistance(BigDecimal travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }
}
