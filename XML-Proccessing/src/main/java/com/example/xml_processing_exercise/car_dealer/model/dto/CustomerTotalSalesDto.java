package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesDto {
    @XmlAttribute(name = "full-name")
    private String fullName;

    @XmlAttribute(name = "bought-cars")
    private Integer boughtCars;

    @XmlAttribute(name = "spent-money")
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
