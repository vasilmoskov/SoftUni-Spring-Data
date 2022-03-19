package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

public class SaleOfOrderedCustomerDto {
    @Expose
    private Long id;

    public SaleOfOrderedCustomerDto() {
    }

    public Long getId() {
        return id;
    }

    public SaleOfOrderedCustomerDto setId(Long id) {
        this.id = id;
        return this;
    }
}
