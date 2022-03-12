package com.example.automappingobjects.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private BigDecimal salary;

    private LocalDate birthday;

    private String address;

    @Column(name = "on_holiday")
    private boolean onHoliday;

    @ManyToOne
    private EmployeeEntity manager;

    @OneToMany
    private List<EmployeeEntity> subordinates;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String firstName, String lastName, BigDecimal salary, LocalDate birthday, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public EmployeeEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public EmployeeEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EmployeeEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public EmployeeEntity setSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public EmployeeEntity setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public EmployeeEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isOnHoliday() {
        return onHoliday;
    }

    public EmployeeEntity setOnHoliday(boolean onHoliday) {
        this.onHoliday = onHoliday;
        return this;
    }

    public EmployeeEntity getManager() {
        return manager;
    }

    public EmployeeEntity setManager(EmployeeEntity manager) {
        this.manager = manager;
        return this;
    }

    public List<EmployeeEntity> getSubordinates() {
        return subordinates;
    }

    public EmployeeEntity setSubordinates(List<EmployeeEntity> subordinates) {
        this.subordinates = subordinates;
        return this;
    }

    public void addEmployee(EmployeeEntity employee) {
        this.subordinates.add(employee);
    }
}
