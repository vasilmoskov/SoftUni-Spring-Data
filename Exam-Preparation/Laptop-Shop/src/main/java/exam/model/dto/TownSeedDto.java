package exam.model.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedDto {
    @XmlElement
    @Size(min = 2)
    private String name;

    @XmlElement
    @Positive
    private Integer population;

    @XmlElement(name = "travel-guide")
    @Size(min = 10)
    private String travelGuide;

    public TownSeedDto() {
    }

    public String getName() {
        return name;
    }

    public TownSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public TownSeedDto setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public TownSeedDto setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
        return this;
    }
}
