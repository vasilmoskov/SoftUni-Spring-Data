package bg.softuni.json_processing_exercise.product_shop.services;

import bg.softuni.json_processing_exercise.product_shop.models.entities.User;

import java.io.IOException;

public interface UserService {

    void seedUsers() throws IOException;

    User getRandomUser();

    void exportUsersBySoldProducts() throws IOException;

    void exportUsersBySoldProductsOrderedByProductsCountDesc() throws IOException;
}
