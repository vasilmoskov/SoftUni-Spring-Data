package exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlNameDto {
    @XmlElement
    private String name;

    public XmlNameDto() {
    }

    public String getName() {
        return name;
    }

    public XmlNameDto setName(String name) {
        this.name = name;
        return this;
    }
}
