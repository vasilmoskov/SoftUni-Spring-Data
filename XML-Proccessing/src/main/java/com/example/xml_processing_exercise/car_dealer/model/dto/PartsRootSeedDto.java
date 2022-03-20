package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsRootSeedDto {

    @XmlElement(name = "part")
    private List<PartSeedDto> parts;

    public PartsRootSeedDto() {
    }

    public List<PartSeedDto> getParts() {
        return parts;
    }

    public PartsRootSeedDto setParts(List<PartSeedDto> parts) {
        this.parts = parts;
        return this;
    }
}
