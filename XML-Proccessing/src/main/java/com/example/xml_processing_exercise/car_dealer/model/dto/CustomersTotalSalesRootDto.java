package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersTotalSalesRootDto {

    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> customers;

    public CustomersTotalSalesRootDto() {
    }

    public List<CustomerTotalSalesDto> getCustomers() {
        return customers;
    }

    public CustomersTotalSalesRootDto setCustomers(List<CustomerTotalSalesDto> customers) {
        this.customers = customers;
        return this;
    }
}
