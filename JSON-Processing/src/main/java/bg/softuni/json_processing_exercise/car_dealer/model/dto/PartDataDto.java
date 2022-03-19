package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartDataDto {
    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    public PartDataDto() {
    }

    public String getName() {
        return name;
    }

    public PartDataDto setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PartDataDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
