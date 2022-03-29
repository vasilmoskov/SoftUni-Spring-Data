package softuni.exam.instagraphlite.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PictureDto {

    @XmlElement
    private String path;

    public PictureDto() {
    }

    public String getPath() {
        return path;
    }

    public PictureDto setPath(String path) {
        this.path = path;
        return this;
    }
}
