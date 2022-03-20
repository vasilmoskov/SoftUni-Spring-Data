package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDto {
    @XmlElement
    private String make;
    @XmlElement
    private String model;
    @XmlElement(name = "travelled-distance")
    private BigInteger travelledDistance;

    public CarSeedDto() {
    }

    public String getMake() {
        return make;
    }

    public CarSeedDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarSeedDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigInteger getTravelledDistance() {
        return travelledDistance;
    }

    public CarSeedDto setTravelledDistance(BigInteger travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }
}
