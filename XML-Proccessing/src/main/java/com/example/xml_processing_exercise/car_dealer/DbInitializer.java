package com.example.xml_processing_exercise.car_dealer;

import com.example.xml_processing_exercise.car_dealer.model.dto.CarSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CarsRootSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CustomerSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.CustomersRootSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.PartSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.PartsRootSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.dto.SuppliersRootSeedDto;
import com.example.xml_processing_exercise.car_dealer.model.entity.CarEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.CustomerEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.PartEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.SaleEntity;
import com.example.xml_processing_exercise.car_dealer.model.entity.SupplierEntity;
import com.example.xml_processing_exercise.car_dealer.repository.CarRepository;
import com.example.xml_processing_exercise.car_dealer.repository.CustomerRepository;
import com.example.xml_processing_exercise.car_dealer.repository.PartRepository;
import com.example.xml_processing_exercise.car_dealer.repository.SaleRepository;
import com.example.xml_processing_exercise.car_dealer.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
public class DbInitializer {
    private static final List<Double> DISCOUNTS = Arrays.asList(0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5);

    private final ModelMapper modelMapper;

    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public DbInitializer(SupplierRepository supplierRepository, ModelMapper modelMapper, PartRepository partRepository, CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    public void seedDb() throws IOException, JAXBException {
        seedSuppliers();
        seedParts();
        seedCars();
        seedCustomers();
        seedSales();
    }

    private void seedSales() {
        if (this.saleRepository.count() > 0) {
            return;
        }

        List<SaleEntity> saleEntities = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            SaleEntity saleEntity = new SaleEntity();

            CustomerEntity customer = getRandomCustomer();

            saleEntity.setCar(getRandomCar())
                    .setCustomer(customer)
                    .setDiscount(getDiscount(customer));

            saleEntities.add(saleEntity);
        }

        this.saleRepository.saveAll(saleEntities);
    }

    private Double getDiscount(CustomerEntity customer) {
        Double discount = DISCOUNTS.get(ThreadLocalRandom.current().nextInt(DISCOUNTS.size()));
        return customer.getYoungDriver() ? discount + 0.05 : discount;
    }

    private CustomerEntity getRandomCustomer() {
        long id = ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1);
        return this.customerRepository.findById(id).orElse(null);
    }

    private CarEntity getRandomCar() {
        long id = ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1);
        return this.carRepository.findById(id).orElse(null);
    }

    private void seedCustomers() throws IOException, JAXBException {
        if (this.customerRepository.count() > 0) {
            return;
        }

        InputStream inputStream = getClass().getResourceAsStream("/files/customers.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomersRootSeedDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CustomersRootSeedDto customersRootSeedDto = (CustomersRootSeedDto) unmarshaller.unmarshal(bufferedReader);

        List<CustomerEntity> customerEntities = customersRootSeedDto.getCustomers()
                .stream()
                .map(this::mapCustomerDtoToEntity)
                .collect(Collectors.toList());

        this.customerRepository.saveAll(customerEntities);
    }

    private CustomerEntity mapCustomerDtoToEntity(CustomerSeedDto dto) {
        CustomerEntity customerEntity = modelMapper.map(dto, CustomerEntity.class);
        customerEntity.setBirthDate(LocalDateTime.parse(dto.getBirthDate(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return customerEntity;
    }

    private void seedCars() throws IOException, JAXBException {
        if (this.carRepository.count() > 0) {
            return;
        }

        InputStream inputStream = getClass().getResourceAsStream("/files/cars.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        JAXBContext jaxbContext = JAXBContext.newInstance(CarsRootSeedDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CarsRootSeedDto carsRootSeedDto = (CarsRootSeedDto) unmarshaller.unmarshal(bufferedReader);

        List<CarEntity> cars = carsRootSeedDto.getCars()
                .stream()
                .map(this::mapCarDtoToEntity)
                .collect(Collectors.toList());

        this.carRepository.saveAll(cars);
    }

    private CarEntity mapCarDtoToEntity(CarSeedDto dto) {
        CarEntity carEntity = modelMapper.map(dto, CarEntity.class);
        carEntity.setParts(getRandomParts());
        return carEntity;
    }

    private List<PartEntity> getRandomParts() {
        List<PartEntity> partEntities = new ArrayList<>();

        long partsCount = ThreadLocalRandom.current().nextLong(10, 20 + 1);

        LongStream ids = ThreadLocalRandom.current()
                .longs(partsCount, 1, this.partRepository.count() + 1);

        ids.forEach(id -> partEntities.add(this.partRepository.findById(id).orElse(null)));

        return partEntities;
    }

    private void seedParts() throws IOException, JAXBException {
        if (partRepository.count() > 0) {
            return;
        }

        InputStream inputStream = getClass().getResourceAsStream("/files/parts.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        JAXBContext jaxbContext = JAXBContext.newInstance(PartsRootSeedDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        PartsRootSeedDto partsRootSeedDto = (PartsRootSeedDto) unmarshaller.unmarshal(bufferedReader);

        List<PartEntity> entities = partsRootSeedDto.getParts()
                .stream()
                .map(this::mapPartDtoToEntity)
                .collect(Collectors.toList());

        this.partRepository.saveAll(entities);
    }

    private PartEntity mapPartDtoToEntity(PartSeedDto dto) {
        PartEntity partEntity = modelMapper.map(dto, PartEntity.class);
        partEntity.setSupplier(getRandomSupplier());
        return partEntity;
    }

    private SupplierEntity getRandomSupplier() {
        long id = ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1);
        return this.supplierRepository.findById(id).orElse(null);
    }

    private void seedSuppliers() throws IOException, JAXBException {
        if (supplierRepository.count() > 0) {
            return;
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(SuppliersRootSeedDto.class);
        InputStream inputStream = getClass().getResourceAsStream("/files/suppliers.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SuppliersRootSeedDto suppliersSeedDto = (SuppliersRootSeedDto) unmarshaller.unmarshal(bufferedReader);

        List<SupplierEntity> entities = suppliersSeedDto.getSuppliers()
                .stream()
                .map(dto -> modelMapper.map(dto, SupplierEntity.class))
                .collect(Collectors.toList());

        this.supplierRepository.saveAll(entities);
    }
}
