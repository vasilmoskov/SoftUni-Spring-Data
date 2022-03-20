package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsAndTheirPartsRootDto {

    @XmlElement(name = "car")
    private List<CarAndItsPartsDto> cars;

    public CarsAndTheirPartsRootDto() {
    }

    public List<CarAndItsPartsDto> getCars() {
        return cars;
    }

    public CarsAndTheirPartsRootDto setCars(List<CarAndItsPartsDto> cars) {
        this.cars = cars;
        return this;
    }
}
