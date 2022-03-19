package bg.softuni.json_processing_exercise.car_dealer;

import bg.softuni.json_processing_exercise.car_dealer.model.dto.CarsSeedDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.CustomersSeedDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.PartsSeedDto;
import bg.softuni.json_processing_exercise.car_dealer.model.dto.SuppliersSeedDto;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.CarEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.CustomerEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.PartEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.SaleEntity;
import bg.softuni.json_processing_exercise.car_dealer.model.entity.SupplierEntity;
import bg.softuni.json_processing_exercise.car_dealer.repository.CarRepository;
import bg.softuni.json_processing_exercise.car_dealer.repository.CustomerRepository;
import bg.softuni.json_processing_exercise.car_dealer.repository.PartRepository;
import bg.softuni.json_processing_exercise.car_dealer.repository.SaleRepository;
import bg.softuni.json_processing_exercise.car_dealer.repository.SupplierRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private final Gson gson;

    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public DbInitializer(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson, PartRepository partRepository, CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    public void seedDb() throws IOException {
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

    private void seedCustomers() throws IOException {
        if (this.customerRepository.count() > 0) {
            return;
        }

        Path path = Path.of("src", "main", "resources", "customers.json");

        String fileContent = String.join("\n", Files.readAllLines(path));

        CustomersSeedDto[] customersSeedDtos = this.gson.fromJson(fileContent, CustomersSeedDto[].class);

        List<CustomerEntity> customerEntities = Arrays.stream(customersSeedDtos)
                .map(this::mapCustomerDtoToEntity)
                .collect(Collectors.toList());

        this.customerRepository.saveAll(customerEntities);
    }

    private CustomerEntity mapCustomerDtoToEntity(CustomersSeedDto dto) {
        CustomerEntity customerEntity = modelMapper.map(dto, CustomerEntity.class);
        customerEntity.setBirthDate(LocalDateTime.parse(dto.getBirthDate(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return customerEntity;
    }

    private void seedCars() throws IOException {
        if (this.carRepository.count() > 0) {
            return;
        }

        Path path = Path.of("src", "main", "resources", "cars.json");

        String fileContent = String.join("\n", Files.readAllLines(path));

        CarsSeedDto[] carsSeedDtos = this.gson.fromJson(fileContent, CarsSeedDto[].class);

        List<CarEntity> cars = Arrays.stream(carsSeedDtos)
                .map(this::mapCarDtoToEntity)
                .collect(Collectors.toList());

        this.carRepository.saveAll(cars);
    }

    private CarEntity mapCarDtoToEntity(CarsSeedDto dto) {
        CarEntity carEntity = modelMapper.map(dto, CarEntity.class);
        carEntity.setParts(getRandomParts());
        return carEntity;
    }

    private List<PartEntity> getRandomParts() {
        List<PartEntity> partEntities = new ArrayList<>();

        long partsCount = ThreadLocalRandom.current().nextLong(3, 5 + 1);

        LongStream ids = ThreadLocalRandom.current()
                .longs(partsCount, 1, this.partRepository.count() + 1);

        ids.forEach(id -> partEntities.add(this.partRepository.findById(id).orElse(null)));

        return partEntities;
    }

    private void seedParts() throws IOException {
        if (partRepository.count() > 0) {
            return;
        }

        Path path = Path.of("src", "main", "resources", "parts.json");
        String fileContent = String.join("\n", Files.readAllLines(path));

        PartsSeedDto[] partsSeedDtos = this.gson.fromJson(fileContent, PartsSeedDto[].class);

        List<PartEntity> entities = Arrays.stream(partsSeedDtos)
                .map(this::mapPartDtoToEntity)
                .collect(Collectors.toList());

        this.partRepository.saveAll(entities);
    }

    private PartEntity mapPartDtoToEntity(PartsSeedDto dto) {
        PartEntity partEntity = modelMapper.map(dto, PartEntity.class);
        partEntity.setSupplier(getRandomSupplier());
        return partEntity;
    }

    private SupplierEntity getRandomSupplier() {
        long id = ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1);
        return this.supplierRepository.findById(id).orElse(null);
    }

    private void seedSuppliers() throws IOException {
        if (supplierRepository.count() > 0) {
            return;
        }

        Path path = Path.of("src", "main", "resources", "suppliers.json");
        String fileContent = String.join("\n", Files.readAllLines(path));

        SuppliersSeedDto[] suppliersSeedDtos = gson.fromJson(fileContent, SuppliersSeedDto[].class);

        List<SupplierEntity> entities = Arrays.stream(suppliersSeedDtos)
                .map(dto -> modelMapper.map(dto, SupplierEntity.class))
                .collect(Collectors.toList());

        this.supplierRepository.saveAll(entities);
    }
}
