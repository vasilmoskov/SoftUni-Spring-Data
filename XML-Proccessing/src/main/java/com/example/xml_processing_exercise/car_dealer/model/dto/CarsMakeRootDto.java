package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsMakeRootDto {

    @XmlElement(name = "car")
    private List<CarMakeDto> cars;

    public CarsMakeRootDto() {
    }

    public List<CarMakeDto> getCars() {
        return cars;
    }

    public CarsMakeRootDto setCars(List<CarMakeDto> cars) {
        this.cars = cars;
        return this;
    }
}
