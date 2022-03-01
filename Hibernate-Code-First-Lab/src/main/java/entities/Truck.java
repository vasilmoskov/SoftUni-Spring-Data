package entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "trucks")
@DiscriminatorValue(value = "truck")
public class Truck extends Vehicle{
    private static final String TYPE = "TRUCK";
    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck() {
    }

    public Truck(String model, BigDecimal price, String fuelType, double loadCapacity) {
        super(TYPE, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
