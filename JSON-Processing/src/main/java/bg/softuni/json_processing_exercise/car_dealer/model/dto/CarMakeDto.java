package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CarMakeDto {
    @Expose
    private Long id;
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private BigDecimal travelledDistance;

    public CarMakeDto() {
    }

    public Long getId() {
        return id;
    }

    public CarMakeDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMake() {
        return make;
    }

    public CarMakeDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarMakeDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigDecimal getTravelledDistance() {
        return travelledDistance;
    }

    public CarMakeDto setTravelledDistance(BigDecimal travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }
}
