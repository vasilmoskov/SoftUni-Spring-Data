package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersRootSeedDto {

    @XmlElement(name = "customer")
    private List<CustomerSeedDto> customers;

    public CustomersRootSeedDto() {
    }

    public List<CustomerSeedDto> getCustomers() {
        return customers;
    }

    public CustomersRootSeedDto setCustomers(List<CustomerSeedDto> customers) {
        this.customers = customers;
        return this;
    }
}
