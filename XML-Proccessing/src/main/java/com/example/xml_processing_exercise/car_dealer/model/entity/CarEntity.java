package com.example.xml_processing_exercise.car_dealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cars")
public class CarEntity extends BaseEntity {
    private String make;
    private String model;
    private BigDecimal travelledDistance;
    private List<PartEntity> parts;

    public CarEntity() {
    }

    public CarEntity(String make, String model, BigDecimal traveledDistance) {
        this.make = make;
        this.model = model;
        this.travelledDistance = traveledDistance;
    }

    @Column
    public String getMake() {
        return make;
    }

    public CarEntity setMake(String make) {
        this.make = make;
        return this;
    }

    @Column
    public String getModel() {
        return model;
    }

    public CarEntity setModel(String model) {
        this.model = model;
        return this;
    }

    @Column(name = "travelled_distance", precision = 65)
    public BigDecimal getTravelledDistance() {
        return travelledDistance;
    }

    public CarEntity setTravelledDistance(BigDecimal traveledDistance) {
        this.travelledDistance = traveledDistance;
        return this;
    }

    @ManyToMany
    @JoinTable(
            name = "parts_cars",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"))
    public List<PartEntity> getParts() {
        return parts;
    }

    public CarEntity setParts(List<PartEntity> parts) {
        this.parts = parts;
        return this;
    }
}
