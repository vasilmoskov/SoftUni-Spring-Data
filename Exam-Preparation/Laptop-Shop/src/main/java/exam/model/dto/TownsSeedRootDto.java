package exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownsSeedRootDto {
    @XmlElement(name = "town")
    private List<TownSeedDto> towns;

    public TownsSeedRootDto() {
    }

    public TownsSeedRootDto(List<TownSeedDto> towns) {
        this.towns = towns;
    }

    public List<TownSeedDto> getTowns() {
        return towns;
    }

    public TownsSeedRootDto setTowns(List<TownSeedDto> towns) {
        this.towns = towns;
        return this;
    }
}
