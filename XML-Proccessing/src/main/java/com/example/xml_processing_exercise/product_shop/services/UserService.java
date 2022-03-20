package com.example.xml_processing_exercise.product_shop.services;

import com.example.xml_processing_exercise.product_shop.models.entities.User;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserService {

    void seedUsers() throws JAXBException, FileNotFoundException;

    User getRandomUser();

    void exportUsersBySoldProducts() throws IOException, JAXBException;

    void exportUsersBySoldProductsOrderedByProductsCountDesc() throws IOException;
}
