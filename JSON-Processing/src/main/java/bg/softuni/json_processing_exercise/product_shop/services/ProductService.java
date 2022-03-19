package bg.softuni.json_processing_exercise.product_shop.services;

import java.io.IOException;

public interface ProductService {
    void seedProducts() throws IOException;

    void exportProductsInPriceRangeWithNoBuyer() throws IOException;
}
