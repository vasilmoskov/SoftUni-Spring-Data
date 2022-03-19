package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigInteger;

public class CarsSeedDto {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private BigInteger travelledDistance;

    public CarsSeedDto() {
    }

    public String getMake() {
        return make;
    }

    public CarsSeedDto setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarsSeedDto setModel(String model) {
        this.model = model;
        return this;
    }

    public BigInteger getTravelledDistance() {
        return travelledDistance;
    }

    public CarsSeedDto setTravelledDistance(BigInteger travelledDistance) {
        this.travelledDistance = travelledDistance;
        return this;
    }
}
