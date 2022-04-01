package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PassengerDto {

    @XmlElement
    private String email;

    public PassengerDto() {
    }

    public String getEmail() {
        return email;
    }

    public PassengerDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
