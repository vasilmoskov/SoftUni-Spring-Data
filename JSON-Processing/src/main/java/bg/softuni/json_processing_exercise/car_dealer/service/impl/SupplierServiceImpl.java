package bg.softuni.json_processing_exercise.car_dealer.service.impl;

import bg.softuni.json_processing_exercise.car_dealer.model.dto.LocalSuppliersDto;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.PartEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.SupplierEntity;
import bg.softuni.json_processing_exercise.car_dealer.repository.SupplierRepository;
import bg.softuni.json_processing_exercise.car_dealer.service.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void exportNonImporters() throws IOException {
        List<LocalSuppliersDto> dtos = this.supplierRepository.findAllByImporter(false)
                .stream().map(this::mapSupplierEntityToLocalSupplierDto)
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter("src/main/resources/out/local-suppliers.json");

        this.gson.toJson(dtos, writer);

        writer.close();
    }

    private LocalSuppliersDto mapSupplierEntityToLocalSupplierDto(SupplierEntity entity) {
        LocalSuppliersDto dto = this.modelMapper.map(entity, LocalSuppliersDto.class);

        int partsCount = entity.getParts()
                .stream()
                .mapToInt(PartEntity::getQuantity)
                .sum();

        dto.setPartsCount(partsCount);
        return dto;
    }
}
