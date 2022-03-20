package com.example.xml_processing_exercise.product_shop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProductService {
    void seedProducts() throws IOException, JAXBException;

    void exportProductsInPriceRangeWithNoBuyer() throws IOException, JAXBException;
}
