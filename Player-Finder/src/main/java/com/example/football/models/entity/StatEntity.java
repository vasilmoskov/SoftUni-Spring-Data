package com.example.football.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class StatEntity extends BaseEntity {
    private Double shooting;
    private Double passing;
    private Double endurance;

    public StatEntity() {
    }

    @Column(nullable = false)
    public Double getShooting() {
        return shooting;
    }

    public StatEntity setShooting(Double shooting) {
        this.shooting = shooting;
        return this;
    }

    @Column(nullable = false)
    public Double getPassing() {
        return passing;
    }

    public StatEntity setPassing(Double passing) {
        this.passing = passing;
        return this;
    }

    @Column(nullable = false)
    public Double getEndurance() {
        return endurance;
    }

    public StatEntity setEndurance(Double endurance) {
        this.endurance = endurance;
        return this;
    }
}
