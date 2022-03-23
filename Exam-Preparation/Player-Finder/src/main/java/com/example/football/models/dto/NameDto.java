package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NameDto {
    @XmlElement
    private String name;

    public NameDto() {
    }

    public String getName() {
        return name;
    }

    public NameDto setName(String name) {
        this.name = name;
        return this;
    }
}
