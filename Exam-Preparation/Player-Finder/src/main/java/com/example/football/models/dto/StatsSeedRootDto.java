package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatsSeedRootDto {

    @XmlElement(name = "stat")
    private List<StatSeedDto> stats;

    public StatsSeedRootDto() {
    }

    public List<StatSeedDto> getStats() {
        return stats;
    }

    public StatsSeedRootDto setStats(List<StatSeedDto> stats) {
        this.stats = stats;
        return this;
    }
}
