package com.example.xml_processing_exercise.car_dealer.service.impl;

import com.example.xml_processing_exercise.car_dealer.model.dto.CustomerTotalSalesDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CustomersTotalSalesRootDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.OrderedCustomerDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.OrderedCustomersRootDto;
import com.example.xml_processing_exercise.car_dealer.model.entity.CarEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.CustomerEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.PartEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.SaleEntity;
import com.example.xml_processing_exercise.car_dealer.repository.CustomerRepository;
import com.example.xml_processing_exercise.car_dealer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void exportOrderedCustomers() throws IOException, JAXBException {
        List<OrderedCustomerDto> dtos = this.customerRepository.findAllByOrderByBirthDateAsc()
                .stream()
                .map(e -> this.modelMapper.map(e, OrderedCustomerDto.class))
                .collect(Collectors.toList());

        OrderedCustomersRootDto rootDto = new OrderedCustomersRootDto()
                .setCustomers(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(OrderedCustomersRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File("src/main/resources/files/out/ordered-customers.xml"));
    }

    @Override
    @Transactional
    public void exportCustomerTotalSales() throws IOException, JAXBException {
        List<CustomerTotalSalesDto> dtos = this.customerRepository.findAllByBoughtCar()
                .stream().map(this::mapCustomerEntityToCustomerTotalSalesDto)
                .sorted(CustomerServiceImpl::compare)
                .collect(Collectors.toList());

        CustomersTotalSalesRootDto rootDto = new CustomersTotalSalesRootDto()
                .setCustomers(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomersTotalSalesRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File("src/main/resources/files/out/customers-total-sales.xml"));
    }

    private static int compare(CustomerTotalSalesDto c1, CustomerTotalSalesDto c2) {
        int res = c2.getSpentMoney().compareTo(c1.getSpentMoney());

        if (res == 0) {
            res = c2.getBoughtCars().compareTo(c1.getBoughtCars());
        }

        return res;
    }

    private CustomerTotalSalesDto mapCustomerEntityToCustomerTotalSalesDto(CustomerEntity entity) {
        Set<SaleEntity> purchases = entity.getPurchases();
        BigDecimal moneySpent = getMoneySpent(purchases);

        return new  CustomerTotalSalesDto()
                .setFullName(entity.getName())
                .setBoughtCars(purchases.size())
                .setSpentMoney(moneySpent);
    }

    private BigDecimal getMoneySpent(Set<SaleEntity> purchases) {
        BigDecimal total = new BigDecimal(0);

        for (SaleEntity purchase : purchases) {
            Double discountPercent = purchase.getDiscount();
            CarEntity car = purchase.getCar();
            List<PartEntity> parts = car.getParts();

            for (PartEntity part : parts) {
                BigDecimal price = part.getPrice();
                BigDecimal discount = price.multiply(BigDecimal.valueOf(discountPercent));
                price = price.subtract(discount);

                total = total.add(price);
            }
        }

        return total.setScale(2, RoundingMode.HALF_EVEN);
    }
}
