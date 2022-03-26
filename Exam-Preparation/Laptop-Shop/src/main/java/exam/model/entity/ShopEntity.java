package exam.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "shops")
public class ShopEntity extends BaseEntity {
    private String name;
    private BigDecimal income;
    private String address;
    private Short employeeCount;
    private Integer shopArea;
    private TownEntity town;

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public ShopEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getIncome() {
        return income;
    }

    public ShopEntity setIncome(BigDecimal income) {
        this.income = income;
        return this;
    }

    @Column(nullable = false)
    public String getAddress() {
        return address;
    }

    public ShopEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    @Column(name = "employee_count", nullable = false)
    public Short getEmployeeCount() {
        return employeeCount;
    }

    public ShopEntity setEmployeeCount(Short employeeCount) {
        this.employeeCount = employeeCount;
        return this;
    }

    @Column(name = "shop_area", nullable = false)
    public Integer getShopArea() {
        return shopArea;
    }

    public ShopEntity setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
        return this;
    }

    @ManyToOne(optional = false)
    public TownEntity getTown() {
        return town;
    }

    public ShopEntity setTown(TownEntity town) {
        this.town = town;
        return this;
    }
}
