package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TeamsSeedDto {
    @Expose
    @Size(min = 3)
    private String name;

    @Expose
    @Size(min = 3)
    private String stadiumName;

    @Expose
    @Min(value = 1000)
    private Long fanBase;

    @Expose
    @Size(min = 10)
    private String history;

    @Expose
    private String townName;

    public TeamsSeedDto() {
    }

    public String getName() {
        return name;
    }

    public TeamsSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public TeamsSeedDto setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
        return this;
    }

    public Long getFanBase() {
        return fanBase;
    }

    public TeamsSeedDto setFanBase(Long fanBase) {
        this.fanBase = fanBase;
        return this;
    }

    public String getHistory() {
        return history;
    }

    public TeamsSeedDto setHistory(String history) {
        this.history = history;
        return this;
    }

    public String getTownName() {
        return townName;
    }

    public TeamsSeedDto setTownName(String townName) {
        this.townName = townName;
        return this;
    }
}
