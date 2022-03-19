package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarAndItsPartsDto {
    @Expose
    private CarDataDto car;

    @Expose
    private List<PartDataDto> parts;

    public CarAndItsPartsDto() {
    }

    public CarDataDto getCar() {
        return car;
    }

    public CarAndItsPartsDto setCar(CarDataDto car) {
        this.car = car;
        return this;
    }

    public List<PartDataDto> getParts() {
        return parts;
    }

    public CarAndItsPartsDto setParts(List<PartDataDto> parts) {
        this.parts = parts;
        return this;
    }
}
