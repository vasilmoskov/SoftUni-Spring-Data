package com.example.xml_processing_exercise.car_dealer.service.impl;

import com.example.xml_processing_exercise.car_dealer.model.dto.LocalSupplierDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.LocalSuppliersRootDto;
import com.example.xml_processing_exercise.car_dealer.model.entity.PartEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.SupplierEntity;
import com.example.xml_processing_exercise.car_dealer.repository.SupplierRepository;
import com.example.xml_processing_exercise.car_dealer.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void exportNonImporters() throws IOException, JAXBException {
        List<LocalSupplierDto> dtos = this.supplierRepository.findAllByImporter(false)
                .stream().map(this::mapSupplierEntityToLocalSupplierDto)
                .collect(Collectors.toList());

        LocalSuppliersRootDto rootDto = new LocalSuppliersRootDto()
                .setSuppliers(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(LocalSuppliersRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File("src/main/resources/files/out/local-suppliers.xml"));
    }

    private LocalSupplierDto mapSupplierEntityToLocalSupplierDto(SupplierEntity entity) {
        LocalSupplierDto dto = this.modelMapper.map(entity, LocalSupplierDto.class);

        int partsCount = entity.getParts()
                .stream()
                .mapToInt(PartEntity::getQuantity)
                .sum();

        dto.setPartsCount(partsCount);
        return dto;
    }
}
