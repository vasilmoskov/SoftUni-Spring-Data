package com.example.automappingobjectsexercise.models.dto;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class GameEditDto {
    private Long id;
    private BigDecimal price;
    private Double size;

    public GameEditDto() {
    }

    public GameEditDto(Long id, BigDecimal price, Double size) {
        this.id = id;
        this.price = price;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Positive(message = "Price should be a positive number")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Positive(message = "Size should be a positive number")
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}


