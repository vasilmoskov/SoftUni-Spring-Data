package bg.softuni.json_processing_exercise.car_dealer.service.impl;

import bg.softuni.json_processing_exercise.car_dealer.model.dto.CarDataDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.CarMakeDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.CarAndItsPartsDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.PartDataDto;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.CarEntity;
import bg.softuni.json_processing_exercise.car_dealer.repository.CarRepository;
import bg.softuni.json_processing_exercise.car_dealer.service.CarService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void exportCarsWithMake(String make) throws IOException {
        List<CarMakeDto> dtos = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream().map(e -> modelMapper.map(e, CarMakeDto.class))
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter("src/main/resources/out/toyota-cars.json");

        this.gson.toJson(dtos, writer);

        writer.close();
    }

    @Override
    @Transactional
    public void exportCarsAndParts() throws IOException {
        List<CarAndItsPartsDto> dtos = this.carRepository.findAll()
                .stream().map(this::mapCarEntityToCarAndPartsDto)
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter("src/main/resources/out/cars-and-parts.json");

        this.gson.toJson(dtos, writer);

        writer.close();
    }

    private CarAndItsPartsDto mapCarEntityToCarAndPartsDto(CarEntity entity) {
        CarAndItsPartsDto dto = new CarAndItsPartsDto();

        CarDataDto carDataDto = this.modelMapper.map(entity, CarDataDto.class);

        List<PartDataDto> partDataDtos = entity.getParts()
                .stream()
                .map(p -> this.modelMapper.map(p, PartDataDto.class))
                .collect(Collectors.toList());

        dto.setCar(carDataDto).setParts(partDataDtos);
        return dto;
    }
}
