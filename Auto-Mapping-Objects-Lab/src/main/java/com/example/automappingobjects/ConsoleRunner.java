package com.example.automappingobjects;

import com.example.automappingobjects.dto.EmployeeDto;
import com.example.automappingobjects.entities.EmployeeEntity;
import com.example.automappingobjects.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private EmployeeRepository employeeRepository;

    public ConsoleRunner(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        Employee employee = this.employeeRepository.findById(1L).orElseThrow();

        EmployeeEntity employee = new EmployeeEntity()
                .setFirstName("Vasil")
                .setLastName("Moskov")
                .setSalary(BigDecimal.valueOf(10))
                .setAddress("Plovdiv");

        ModelMapper modelMapper = new ModelMapper();

//        PropertyMap<EmployeeDto, Employee> employeeMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                map().setFirstName(source.getFirstName());
//                map().setLastName(source.getLastName());
//                skip().setSalary(null);
//            }
//        };

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        System.out.println(employeeDto);

//        EmployeeDto employeeDto = new EmployeeDto();

//        modelMapper.addMappings(employeeMap).map(employeeDto, employee);

        /*
        TypeMap<EmployeeDto, Employee> typeMap = modelMapper.createTypeMap(EmployeeDto.class, Employee.class);
        typeMap.addMappings(m -> m.map(src -> src.getFirstName(), Employee::setFirstName));
        typeMap.map(employeeDto);
         */


//        System.out.println(employeeDto.getFirstName());
//        System.out.println(employeeDto.getLastName());
//        System.out.println(employeeDto.getSalary());
    }
}
