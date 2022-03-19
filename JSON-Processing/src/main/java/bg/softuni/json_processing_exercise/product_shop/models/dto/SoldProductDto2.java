package bg.softuni.json_processing_exercise.product_shop.models.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SoldProductDto2 {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public SoldProductDto2() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
