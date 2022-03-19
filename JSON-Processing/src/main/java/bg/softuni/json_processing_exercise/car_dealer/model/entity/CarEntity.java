package bg.softuni.json_processing_exercise.car_dealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "cars")
public class CarEntity extends BaseEntity {
    private String make;
    private String model;
    private String travelledDistance;
    private List<PartEntity> parts;

    public CarEntity() {
    }

    public CarEntity(String make, String model, String traveledDistance) {
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

    @Column(name = "travelled_distance")
    @Lob
    public String getTravelledDistance() {
        return travelledDistance;
    }

    public CarEntity setTravelledDistance(String traveledDistance) {
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
