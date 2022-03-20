package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsRootSeedDto {

    @XmlElement(name = "car")
    private List<CarSeedDto> cars;

    public CarsRootSeedDto() {
    }

    public List<CarSeedDto> getCars() {
        return cars;
    }

    public CarsRootSeedDto setCars(List<CarSeedDto> cars) {
        this.cars = cars;
        return this;
    }
}
