package exam.model.dto;

import com.google.gson.annotations.Expose;
import exam.model.entity.WarrantyTypeEnum;
import exam.util.WarrantyTypeSubset;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class LaptopSeedDto {

    @Expose
    @Size(min = 9)
    private String macAddress;

    @Expose
    @Positive
    private Float cpuSpeed;

    @Expose
    @Min(8)
    @Max(128)
    private Short ram;

    @Expose
    @Min(128)
    @Max(1024)
    private Short storage;

    @Expose
    @Size(min = 10)
    private String description;

    @Expose
    @Positive
    private BigDecimal price;

    @Expose
    //@NotNull - works just fine but played with custom validator :-)
    @WarrantyTypeSubset(anyOf = {WarrantyTypeEnum.BASIC, WarrantyTypeEnum.LIFETIME, WarrantyTypeEnum.PREMIUM})
    private WarrantyTypeEnum warrantyType;

    @Expose
    private JsonNameDto shop;

    public String getMacAddress() {
        return macAddress;
    }

    public LaptopSeedDto setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public Float getCpuSpeed() {
        return cpuSpeed;
    }

    public LaptopSeedDto setCpuSpeed(Float cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
        return this;
    }

    public Short getRam() {
        return ram;
    }

    public LaptopSeedDto setRam(Short ram) {
        this.ram = ram;
        return this;
    }

    public Short getStorage() {
        return storage;
    }

    public LaptopSeedDto setStorage(Short storage) {
        this.storage = storage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LaptopSeedDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LaptopSeedDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public WarrantyTypeEnum getWarrantyType() {
        return warrantyType;
    }

    public LaptopSeedDto setWarrantyType(WarrantyTypeEnum warrantyType) {
        this.warrantyType = warrantyType;
        return this;
    }

    public JsonNameDto getShop() {
        return shop;
    }

    public LaptopSeedDto setShop(JsonNameDto shop) {
        this.shop = shop;
        return this;
    }
}
