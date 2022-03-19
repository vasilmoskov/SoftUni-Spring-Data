package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

public class LocalSuppliersDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Integer partsCount;

    public LocalSuppliersDto() {
    }

    public Long getId() {
        return id;
    }

    public LocalSuppliersDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocalSuppliersDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public LocalSuppliersDto setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
        return this;
    }
}
