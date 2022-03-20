package com.example.xml_processing_exercise.product_shop.services;

import com.example.xml_processing_exercise.product_shop.models.entities.Category;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException, JAXBException;

    Set<Category> getRandomCategories();

    void exportCategoriesByProductsCount() throws IOException;
}
