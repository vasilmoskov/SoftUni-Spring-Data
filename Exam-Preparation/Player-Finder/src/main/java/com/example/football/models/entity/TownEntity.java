package com.example.football.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "towns")
public class TownEntity extends BaseEntity {
    private String name;
    private Integer population;
    private String travelGuide;

    public TownEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public TownEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public Integer getPopulation() {
        return population;
    }

    public TownEntity setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    @Column(nullable = false)
    @Lob
    public String getTravelGuide() {
        return travelGuide;
    }

    public TownEntity setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
        return this;
    }
}
