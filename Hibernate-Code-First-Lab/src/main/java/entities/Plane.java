package entities;

import com.sun.istack.Nullable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "planes")
@DiscriminatorValue(value = "plane")
public class Plane extends Vehicle{
    private static final String TYPE = "PLANE";

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Plane() {
    }

    public Plane(String model, BigDecimal price, String fuelType, int passengerCapacity, Company company) {
        super(TYPE, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
        this.company = company;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}
