package bg.softuni.json_processing_exercise.product_shop;

import bg.softuni.json_processing_exercise.product_shop.services.CategoryService;
import bg.softuni.json_processing_exercise.product_shop.services.ProductService;
import bg.softuni.json_processing_exercise.product_shop.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public CommandLineRunnerImpl(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();

//        this.productService.exportProductsInPriceRangeWithNoBuyer();

//        this.userService.exportUsersBySoldProducts();

//        this.categoryService.exportCategoriesByProductsCount();

        this.userService.exportUsersBySoldProductsOrderedByProductsCountDesc();
    }


    private void seedData() throws IOException {
        userService.seedUsers();
        categoryService.seedCategories();
        productService.seedProducts();
    }
}
