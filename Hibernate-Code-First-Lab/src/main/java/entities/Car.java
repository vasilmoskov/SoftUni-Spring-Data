package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cars")
@DiscriminatorValue(value = "car")
public class Car extends Vehicle{
    private static final String TYPE = "CAR";

    private int seats;

    @OneToOne(optional = false)
    @JoinColumn(name = "plateNumber_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    @ManyToMany()
    @JoinTable(name = "cars_drivers",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"))
    private Set<Driver> drivers;

    public Car() {
    }

    public Car(String model, BigDecimal price, String fuelType, int seats, PlateNumber plateNumber, Set<Driver> drivers) {
        super(TYPE, model, price, fuelType);
        this.seats = seats;
        this.plateNumber = plateNumber;
        this.drivers = drivers;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
