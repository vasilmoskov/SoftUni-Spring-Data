package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanesSeedRootDto {

    @XmlElement(name = "plane")
    private List<PlaneSeedDto> planes;

    public PlanesSeedRootDto() {
    }

    public List<PlaneSeedDto> getPlanes() {
        return planes;
    }

    public PlanesSeedRootDto setPlanes(List<PlaneSeedDto> planes) {
        this.planes = planes;
        return this;
    }
}
