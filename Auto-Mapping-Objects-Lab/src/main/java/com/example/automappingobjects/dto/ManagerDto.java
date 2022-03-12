package com.example.automappingobjects.dto;

import com.example.automappingobjects.entities.EmployeeEntity;

import java.util.List;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeEntity> subordinates;

    public String getFirstName() {
        return firstName;
    }

    public ManagerDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ManagerDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<EmployeeEntity> getSubordinates() {
        return subordinates;
    }

    public ManagerDto setSubordinates(List<EmployeeEntity> subordinates) {
        this.subordinates = subordinates;
        return this;
    }
}
