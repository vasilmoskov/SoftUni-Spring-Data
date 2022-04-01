package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedDto {
    @XmlElement(name = "register-number")
    @Size(min = 5)
    private String registerNumber;

    @XmlElement
    @Positive
    private Integer capacity;

    @XmlElement
    @Size(min = 2)
    private String airline;

    public PlaneSeedDto() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public PlaneSeedDto setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public PlaneSeedDto setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getAirline() {
        return airline;
    }

    public PlaneSeedDto setAirline(String airline) {
        this.airline = airline;
        return this;
    }
}
