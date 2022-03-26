package exam.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedDto {
    @XmlElement
    @Size(min = 4)
    private String address;

    @XmlElement(name = "employee-count")
    @Min(1)
    @Max(50)
    private Short employeeCount;

    @XmlElement
    @Min(20000)
    private BigDecimal income;

    @XmlElement
    @Size(min = 4)
    private String name;

    @XmlElement(name = "shop-area")
    @Min(150)
    private Integer shopArea;

    @XmlElement
    private XmlNameDto town;

    public ShopSeedDto() {
    }

    public String getAddress() {
        return address;
    }

    public ShopSeedDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public Short getEmployeeCount() {
        return employeeCount;
    }

    public ShopSeedDto setEmployeeCount(Short employeeCount) {
        this.employeeCount = employeeCount;
        return this;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public ShopSeedDto setIncome(BigDecimal income) {
        this.income = income;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShopSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public ShopSeedDto setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
        return this;
    }

    public XmlNameDto getTown() {
        return town;
    }

    public ShopSeedDto setTown(XmlNameDto town) {
        this.town = town;
        return this;
    }
}
