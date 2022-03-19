package bg.softuni.json_processing_exercise.car_dealer.service.impl;

import bg.softuni.json_processing_exercise.car_dealer.model.dto.SaleDiscountDto;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.PartEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.SaleEntity;
import bg.softuni.json_processing_exercise.car_dealer.repository.SaleRepository;
import bg.softuni.json_processing_exercise.car_dealer.service.SaleService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper modelMapper, Gson gson) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void exportSalesDiscounts() throws IOException {
        List<SaleDiscountDto> dtos = this.saleRepository.findAll()
                .stream().map(this::mapSaleEntityToSaleDiscountDto)
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter("src/main/resources/out/sales-discounts.json");

        this.gson.toJson(dtos, writer);

        writer.close();
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
