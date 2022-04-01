package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeoff;

    @XmlElement(name = "from-town")
    private TownDto fromTown;

    @XmlElement(name = "to-town")
    private TownDto toTown;

    @XmlElement
    private PassengerDto passenger;

    @XmlElement
    private PlaneDto plane;

    public TicketSeedDto() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public TicketSeedDto setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TicketSeedDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public TicketSeedDto setTakeoff(String takeoff) {
        this.takeoff = takeoff;
        return this;
    }

    public TownDto getFromTown() {
        return fromTown;
    }

    public TicketSeedDto setFromTown(TownDto fromTown) {
        this.fromTown = fromTown;
        return this;
    }

    public TownDto getToTown() {
        return toTown;
    }

    public TicketSeedDto setToTown(TownDto toTown) {
        this.toTown = toTown;
        return this;
    }

    public PassengerDto getPassenger() {
        return passenger;
    }

    public TicketSeedDto setPassenger(PassengerDto passenger) {
        this.passenger = passenger;
        return this;
    }

    public PlaneDto getPlane() {
        return plane;
    }

    public TicketSeedDto setPlane(PlaneDto plane) {
        this.plane = plane;
        return this;
    }
}
