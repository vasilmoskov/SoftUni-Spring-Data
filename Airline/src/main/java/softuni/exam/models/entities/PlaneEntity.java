package softuni.exam.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
public class PlaneEntity extends BaseEntity{
    @Column(unique = true)
    private String registerNumber;

    @Column
    private Integer capacity;

    @Column
    private String airline;

    public String getRegisterNumber() {
        return registerNumber;
    }

    public PlaneEntity setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public PlaneEntity setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getAirline() {
        return airline;
    }

    public PlaneEntity setAirline(String airline) {
        this.airline = airline;
        return this;
    }
}
