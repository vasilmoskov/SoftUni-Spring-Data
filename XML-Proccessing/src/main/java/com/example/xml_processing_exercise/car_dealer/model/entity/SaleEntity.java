package com.example.xml_processing_exercise.car_dealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class SaleEntity extends BaseEntity {
    private CarEntity car;
    private CustomerEntity customer;
    private Double discount;

    public SaleEntity() {
    }

    @OneToOne
    public CarEntity getCar() {
        return car;
    }

    public SaleEntity setCar(CarEntity car) {
        this.car = car;
        return this;
    }

    @ManyToOne
    public CustomerEntity getCustomer() {
        return customer;
    }

    public SaleEntity setCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    @Column
    public Double getDiscount() {
        return discount;
    }

    public SaleEntity setDiscount(Double discountPercentage) {
        this.discount = discountPercentage;
        return this;
    }
}
