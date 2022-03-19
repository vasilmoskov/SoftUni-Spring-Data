package bg.softuni.json_processing_exercise.product_shop.services;

import bg.softuni.json_processing_exercise.product_shop.models.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

    void exportCategoriesByProductsCount() throws IOException;
}
