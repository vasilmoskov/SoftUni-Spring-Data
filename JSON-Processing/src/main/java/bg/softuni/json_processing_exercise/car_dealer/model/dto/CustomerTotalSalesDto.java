package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CustomerTotalSalesDto {
    @Expose
    private String fullName;
    @Expose
    private Integer boughtCars;
    @Expose
    private BigDecimal spentMoney;

    public CustomerTotalSalesDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerTotalSalesDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public CustomerTotalSalesDto setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
        return this;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public CustomerTotalSalesDto setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
        return this;
    }
}
