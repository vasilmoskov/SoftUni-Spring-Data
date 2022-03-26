package exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopsSeedRootDto {
    @XmlElement(name = "shop")
    private List<ShopSeedDto> shops;

    public ShopsSeedRootDto() {
    }

    public ShopsSeedRootDto(List<ShopSeedDto> shops) {
        this.shops = shops;
    }

    public List<ShopSeedDto> getShops() {
        return shops;
    }

    public ShopsSeedRootDto setShops(List<ShopSeedDto> shops) {
        this.shops = shops;
        return this;
    }
}
