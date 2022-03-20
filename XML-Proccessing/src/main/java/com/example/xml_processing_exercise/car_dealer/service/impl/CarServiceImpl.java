package com.example.xml_processing_exercise.car_dealer.service.impl;

import com.example.xml_processing_exercise.car_dealer.model.dto.CarsAndTheirPartsRootDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CarAndItsPartsDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CarMakeDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CarsMakeRootDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.PartDataDto;
import com.example.xml_processing_exercise.car_dealer.model.entity.CarEntity;
import com.example.xml_processing_exercise.car_dealer.repository.CarRepository;
import com.example.xml_processing_exercise.car_dealer.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void exportCarsWithMake(String make) throws IOException, JAXBException {
        List<CarMakeDto> dtos = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream().map(e -> modelMapper.map(e, CarMakeDto.class))
                .collect(Collectors.toList());

        CarsMakeRootDto rootDto = new CarsMakeRootDto()
                .setCars(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(CarsMakeRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File("src/main/resources/files/out/toyota-cars.xml"));
    }

    @Override
    @Transactional
    public void exportCarsAndParts() throws IOException, JAXBException {
        List<CarAndItsPartsDto> dtos = this.carRepository.findAll()
                .stream().map(this::mapCarEntityToCarAndPartsDto)
                .collect(Collectors.toList());

        CarsAndTheirPartsRootDto rootDto = new CarsAndTheirPartsRootDto()
                .setCars(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(CarsAndTheirPartsRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File("src/main/resources/files/out/cars-and-parts.xml"));
    }

    private CarAndItsPartsDto mapCarEntityToCarAndPartsDto(CarEntity entity) {
        CarAndItsPartsDto carDataDto = this.modelMapper.map(entity, CarAndItsPartsDto.class);

        List<PartDataDto> partDataDtos = entity.getParts()
                .stream()
                .map(p -> this.modelMapper.map(p, PartDataDto.class))
                .collect(Collectors.toList());

        carDataDto.setParts(partDataDtos);
        return carDataDto;
    }
}
