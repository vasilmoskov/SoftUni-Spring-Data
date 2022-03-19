package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.List;

public class OrderedCustomersDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private Boolean isYoungDriver;
    @Expose
    private List<SaleOfOrderedCustomerDto> sales;

    public OrderedCustomersDto() {
    }

    public Long getId() {
        return id;
    }

    public OrderedCustomersDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderedCustomersDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public OrderedCustomersDto setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public OrderedCustomersDto setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
        return this;
    }

    public List<SaleOfOrderedCustomerDto> getSales() {
        return sales;
    }

    public OrderedCustomersDto setSales(List<SaleOfOrderedCustomerDto> sales) {
        this.sales = sales;
        return this;
    }
}
