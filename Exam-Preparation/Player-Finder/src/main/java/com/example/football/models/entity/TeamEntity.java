package com.example.football.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class TeamEntity extends BaseEntity {
    private String name;
    private String stadiumName;
    private Long fanBase;
    private String history;
    private TownEntity town;

    public TeamEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public TeamEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public String getStadiumName() {
        return stadiumName;
    }

    public TeamEntity setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
        return this;
    }

    @Column(nullable = false)
    public Long getFanBase() {
        return fanBase;
    }

    public TeamEntity setFanBase(Long fanBase) {
        this.fanBase = fanBase;
        return this;
    }

    @Column(nullable = false)
    @Lob
    public String getHistory() {
        return history;
    }

    public TeamEntity setHistory(String history) {
        this.history = history;
        return this;
    }

    @ManyToOne(optional = false)
    public TownEntity getTown() {
        return town;
    }

    public TeamEntity setTown(TownEntity town) {
        this.town = town;
        return this;
    }
}
