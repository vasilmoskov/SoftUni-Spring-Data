package exam.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "laptops")
public class LaptopEntity extends BaseEntity {
    private String macAddress;
    private Float cpuSpeed;
    private Short ram;
    private Short storage;
    private String description;
    private BigDecimal price;
    private WarrantyTypeEnum warrantyType;
    private ShopEntity shop;

    @Column(name = "mac_address", nullable = false, unique = true)
    public String getMacAddress() {
        return macAddress;
    }

    public LaptopEntity setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    @Column(name = "cpu_speed", nullable = false)
    public Float getCpuSpeed() {
        return cpuSpeed;
    }

    public LaptopEntity setCpuSpeed(Float cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
        return this;
    }

    @Column(nullable = false)
    public Short getRam() {
        return ram;
    }

    public LaptopEntity setRam(Short ram) {
        this.ram = ram;
        return this;
    }

    @Column(nullable = false)
    public Short getStorage() {
        return storage;
    }

    public LaptopEntity setStorage(Short storage) {
        this.storage = storage;
        return this;
    }

    @Column(columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    public LaptopEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public LaptopEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column(name = "warranty_type", nullable = false)
    public WarrantyTypeEnum getWarrantyType() {
        return warrantyType;
    }

    public LaptopEntity setWarrantyType(WarrantyTypeEnum warrantyType) {
        this.warrantyType = warrantyType;
        return this;
    }

    @ManyToOne(optional = false)
    public ShopEntity getShop() {
        return shop;
    }

    public LaptopEntity setShop(ShopEntity shop) {
        this.shop = shop;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Laptop - %s\n" +
                        "*Cpu speed - %.2f\n" +
                        "**Ram - %d\n" +
                        "***Storage - %d\n" +
                        "****Price - %.2f\n" +
                        "#Shop name - %s\n" +
                        "##Town - %s",
                getMacAddress(), getCpuSpeed(), getRam(), getStorage(), getPrice(), getShop().getName(),
                getShop().getTown().getName());
    }
}
