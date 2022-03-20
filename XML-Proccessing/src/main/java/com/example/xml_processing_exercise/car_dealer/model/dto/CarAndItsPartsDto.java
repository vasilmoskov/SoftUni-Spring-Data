package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.math.BigDecimal;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndItsPartsDto {
    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private BigDecimal travelledDistance;

    @XmlElementWrapper(name = "parts")
    @XmlElement(name = "part")
    private List<PartDataDto> parts;

    public CarAndItsPartsDto() {
    }

    public String getMake() {
        return make;
    }

    public CarAndItsPartsDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarAndItsPartsDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigDecimal getTravelledDistance() {
        return travelledDistance;
    }

    public CarAndItsPartsDto setTravelledDistance(BigDecimal travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }

    public List<PartDataDto> getParts() {
        return parts;
    }

    public CarAndItsPartsDto setParts(List<PartDataDto> parts) {
        this.parts = parts;
        return this;
    }
}
