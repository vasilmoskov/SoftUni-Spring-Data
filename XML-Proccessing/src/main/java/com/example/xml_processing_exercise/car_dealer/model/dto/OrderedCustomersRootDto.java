package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderedCustomersRootDto {

    @XmlElement(name = "customer")
    private List<OrderedCustomerDto> customers;

    public OrderedCustomersRootDto() {
    }

    public List<OrderedCustomerDto> getCustomers() {
        return customers;
    }

    public OrderedCustomersRootDto setCustomers(List<OrderedCustomerDto> customers) {
        this.customers = customers;
        return this;
    }
}
