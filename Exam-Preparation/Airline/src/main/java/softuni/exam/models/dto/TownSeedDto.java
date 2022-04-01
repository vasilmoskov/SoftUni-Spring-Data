package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownSeedDto {
    @Expose
    @Size(min = 2)
    private String name;

    @Expose
    @Positive
    private Integer population;

    @Expose
    private String guide;

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

    public String getGuide() {
        return guide;
    }

    public TownSeedDto setGuide(String guide) {
        this.guide = guide;
        return this;
    }
}
