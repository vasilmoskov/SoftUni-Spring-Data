package bg.softuni.json_processing_exercise.car_dealer.service.impl;

import bg.softuni.json_processing_exercise.car_dealer.model.dto.CustomerTotalSalesDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.OrderedCustomersDto;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.CarEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.CustomerEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.PartEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.SaleEntity;
import bg.softuni.json_processing_exercise.car_dealer.repository.CustomerRepository;
import bg.softuni.json_processing_exercise.car_dealer.service.CustomerService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileWriter;
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
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void exportOrderedCustomers() throws IOException {
        List<OrderedCustomersDto> dtos = this.customerRepository.findAllByOrderByBirthDateAsc()
                .stream()
                .map(e -> this.modelMapper.map(e, OrderedCustomersDto.class))
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter("src/main/resources/out/ordered-customers.json");

        this.gson.toJson(dtos, writer);

        writer.close();
    }

    @Override
    @Transactional
    public void exportCustomerTotalSales() throws IOException {
        List<CustomerTotalSalesDto> dtos = this.customerRepository.findAllByBoughtCar()
                .stream().map(this::mapCustomerEntityToCustomerTotalSalesDto)
                .sorted(CustomerServiceImpl::compare)
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter("src/main/resources/out/customers-total-sales.json");

        this.gson.toJson(dtos, writer);

        writer.close();
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
