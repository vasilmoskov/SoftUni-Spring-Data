package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SaleDiscountDto {
    @Expose
    private CarDataDto car;

    @Expose
    private String customerName;

    @Expose
    private Double discount;

    @Expose
    private BigDecimal price;

    @Expose
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
