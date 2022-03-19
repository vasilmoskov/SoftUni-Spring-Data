package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

public class CustomersSeedDto {
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private Boolean isYoungDriver;

    public CustomersSeedDto() {
    }

    public String getName() {
        return name;
    }

    public CustomersSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public CustomersSeedDto setBirthDate(String bithtDate) {
        this.birthDate = bithtDate;
        return this;
    }

    public Boolean isYoungDriver() {
        return isYoungDriver;
    }

    public CustomersSeedDto setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
        return this;
    }
}
