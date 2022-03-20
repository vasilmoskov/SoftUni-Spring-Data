package com.example.xml_processing_exercise.car_dealer.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {
    void exportOrderedCustomers() throws IOException, JAXBException;

    void exportCustomerTotalSales() throws IOException, JAXBException;
}
