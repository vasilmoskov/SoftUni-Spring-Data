package com.example.xml_processing_exercise.car_dealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity{
    private String name;
    private LocalDateTime birthDate;
    private Boolean isYoungDriver;

    private Set<SaleEntity> purchases;

    public CustomerEntity() {
    }

    @Column
    public String getName() {
        return name;
    }

    public CustomerEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "birth_date")
    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public CustomerEntity setBirthDate(LocalDateTime birthdate) {
        this.birthDate = birthdate;
        return this;
    }

    @Column(name = "is_young_driver")
    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public CustomerEntity setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
        return this;
    }

    @OneToMany(mappedBy = "customer")
    public Set<SaleEntity> getPurchases() {
        return purchases;
    }

    public CustomerEntity setPurchases(Set<SaleEntity> sales) {
        this.purchases = sales;
        return this;
    }

    public int totalBoughts() {
        return 2;
    }
}
