package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatSeedDto {
    @XmlElement
    @Positive
    private Double passing;

    @XmlElement
    @Positive
    private Double shooting;

    @XmlElement
    @Positive
    private Double endurance;

    public StatSeedDto() {
    }

    public Double getPassing() {
        return passing;
    }

    public StatSeedDto setPassing(Double passing) {
        this.passing = passing;
        return this;
    }

    public Double getShooting() {
        return shooting;
    }

    public StatSeedDto setShooting(Double shooting) {
        this.shooting = shooting;
        return this;
    }

    public Double getEndurance() {
        return endurance;
    }

    public StatSeedDto setEndurance(Double endurance) {
        this.endurance = endurance;
        return this;
    }
}
