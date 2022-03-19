package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CarDataDto {
    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private BigDecimal travelledDistance;

    public CarDataDto() {
    }

    public String getMake() {
        return make;
    }

    public CarDataDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarDataDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigDecimal getTravelledDistance() {
        return travelledDistance;
    }

    public CarDataDto setTravelledDistance(BigDecimal travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }
}
