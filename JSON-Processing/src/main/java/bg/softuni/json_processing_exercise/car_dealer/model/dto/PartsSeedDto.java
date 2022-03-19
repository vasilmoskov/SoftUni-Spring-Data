package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartsSeedDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private Integer quantity;

    public PartsSeedDto() {
    }

    public String getName() {
        return name;
    }

    public PartsSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PartsSeedDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PartsSeedDto setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
