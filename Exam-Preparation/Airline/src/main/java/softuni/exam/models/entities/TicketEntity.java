package softuni.exam.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class TicketEntity extends BaseEntity {
    @Column
    private String serialNumber;

    @Column
    private BigDecimal price;

    @Column
    private LocalDateTime takeoff;

    @ManyToOne
    private TownEntity fromTown;

    @ManyToOne
    private TownEntity toTown;

    @ManyToOne
    private PassengerEntity passenger;

    @ManyToOne
    private PlaneEntity plane;

    public String getSerialNumber() {
        return serialNumber;
    }

    public TicketEntity setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TicketEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getTakeoff() {
        return takeoff;
    }

    public TicketEntity setTakeoff(LocalDateTime takeoff) {
        this.takeoff = takeoff;
        return this;
    }

    public TownEntity getFromTown() {
        return fromTown;
    }

    public TicketEntity setFromTown(TownEntity fromTown) {
        this.fromTown = fromTown;
        return this;
    }

    public TownEntity getToTown() {
        return toTown;
    }

    public TicketEntity setToTown(TownEntity toTown) {
        this.toTown = toTown;
        return this;
    }

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public TicketEntity setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
        return this;
    }

    public PlaneEntity getPlane() {
        return plane;
    }

    public TicketEntity setPlane(PlaneEntity plane) {
        this.plane = plane;
        return this;
    }
}
