package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneDto {

    @XmlElement(name = "register-number")
    private String registerNumber;

    public PlaneDto() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public PlaneDto setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }
}
