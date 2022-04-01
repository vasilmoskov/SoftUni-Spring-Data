package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownDto {
    @XmlElement
    private String name;

    public TownDto() {
    }

    public String getName() {
        return name;
    }

    public TownDto setName(String name) {
        this.name = name;
        return this;
    }
}
