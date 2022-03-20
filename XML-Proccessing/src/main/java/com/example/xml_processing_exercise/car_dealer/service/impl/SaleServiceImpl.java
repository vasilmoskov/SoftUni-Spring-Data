package com.example.xml_processing_exercise.car_dealer.service.impl;

import com.example.xml_processing_exercise.car_dealer.model.dto.SaleDiscountDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.SalesDiscountsRootDto;
import com.example.xml_processing_exercise.car_dealer.model.entity.PartEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.SaleEntity;
import com.example.xml_processing_exercise.car_dealer.repository.SaleRepository;
import com.example.xml_processing_exercise.car_dealer.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void exportSalesDiscounts() throws IOException, JAXBException {
        List<SaleDiscountDto> dtos = this.saleRepository.findAll()
                .stream().map(this::mapSaleEntityToSaleDiscountDto)
                .collect(Collectors.toList());

        SalesDiscountsRootDto rootDto = new SalesDiscountsRootDto()
                .setSales(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(SalesDiscountsRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File("src/main/resources/files/out/sales-discounts.xml"));
    }

    private SaleDiscountDto mapSaleEntityToSaleDiscountDto(SaleEntity saleEntity) {
        BigDecimal totalPrice = new BigDecimal(0);

        for (PartEntity part : saleEntity.getCar().getParts()) {
            BigDecimal price = part.getPrice();
            totalPrice = price.add(price);
        }

        BigDecimal discountTotal = totalPrice.multiply(BigDecimal.valueOf(saleEntity.getDiscount()));
        BigDecimal priceWithDiscount = totalPrice.subtract(discountTotal);

        return this.modelMapper.map(saleEntity, SaleDiscountDto.class)
                .setPrice(totalPrice)
                .setPriceWithDiscount(priceWithDiscount);
    }
}
