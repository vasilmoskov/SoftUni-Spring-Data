package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountDto {
    @XmlElement
    private CarDataDto car;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement
    private Double discount;

    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;

    public SaleDiscountDto() {
    }

    public CarDataDto getCar() {
        return car;
    }

    public SaleDiscountDto setCar(CarDataDto car) {
        this.car = car;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public SaleDiscountDto setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public Double getDiscount() {
        return discount;
    }

    public SaleDiscountDto setDiscount(Double discount) {
        this.discount = discount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SaleDiscountDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public SaleDiscountDto setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
        return this;
    }
}
