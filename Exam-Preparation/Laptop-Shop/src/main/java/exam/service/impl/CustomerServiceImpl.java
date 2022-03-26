package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.CustomerSeedDto;
import exam.model.entity.CustomerEntity;
import exam.model.entity.TownEntity;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Path path = Path.of("src", "main", "resources", "files", "json", "customers.json");

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, Gson gson,
                               @Qualifier("withLocalDate") ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importCustomers() throws IOException {
        String customersJson = this.readCustomersFileContent();

        List<String> messages = new ArrayList<>();

        CustomerSeedDto[] dtos = this.gson.fromJson(customersJson, CustomerSeedDto[].class);

        for (CustomerSeedDto dto : dtos) {
            Optional<CustomerEntity> customerOpt = this.customerRepository.findByEmail(dto.getEmail());

            if (this.validationUtil.isValid(dto) && customerOpt.isEmpty()) {
                TownEntity townEntity = this.townRepository.findByName(dto.getTown().getName()).orElseThrow();

                CustomerEntity customerEntity = this.modelMapper.map(dto, CustomerEntity.class)
                        .setTown(townEntity);

                this.customerRepository.save(customerEntity);
                messages.add(String.format("Successfully imported Customer %s %s - %s",
                        dto.getFirstName(), dto.getLastName(), dto.getEmail()));
            } else  {
                messages.add("Invalid Customer");
            }
        }

        return String.join("\n", messages);
    }
}
