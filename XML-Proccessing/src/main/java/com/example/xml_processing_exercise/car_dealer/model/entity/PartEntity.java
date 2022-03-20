package com.example.xml_processing_exercise.car_dealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "parts")
public class PartEntity extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private SupplierEntity supplier;

    public PartEntity() {
    }

    public PartEntity(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Column
    public String getName() {
        return name;
    }

    public PartEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public PartEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    public PartEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @ManyToOne
    public SupplierEntity getSupplier() {
        return supplier;
    }

    public PartEntity setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
        return this;
    }
}
