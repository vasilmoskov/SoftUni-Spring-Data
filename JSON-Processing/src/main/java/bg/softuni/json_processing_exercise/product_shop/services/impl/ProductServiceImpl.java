package bg.softuni.json_processing_exercise.product_shop.services.impl;

import bg.softuni.json_processing_exercise.product_shop.models.dto.ProductInPriceRangeDto;
import bg.softuni.json_processing_exercise.product_shop.models.dto.ProductSeedDto;
import bg.softuni.json_processing_exercise.product_shop.models.entities.User;
import bg.softuni.json_processing_exercise.product_shop.models.entities.Category;
import bg.softuni.json_processing_exercise.product_shop.models.entities.Product;
import bg.softuni.json_processing_exercise.product_shop.services.UserService;
import bg.softuni.json_processing_exercise.product_shop.repositories.ProductRepository;
import bg.softuni.json_processing_exercise.product_shop.services.CategoryService;
import bg.softuni.json_processing_exercise.product_shop.services.ProductService;
import bg.softuni.json_processing_exercise.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_FILE_NAME = "src/main/resources/products.json";
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService,
                              CategoryService categoryService, ModelMapper modelMapper,
                              ValidationUtil validationUtil, Gson gson) {

        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() != 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(PRODUCTS_FILE_NAME));

        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);

                    User seller = this.userService.getRandomUser();
                    Set<Category> categories = this.categoryService.getRandomCategories();

                    int random = ThreadLocalRandom.current().nextInt();

                    if (random % 2 == 0) {
                        User buyer = this.userService.getRandomUser();
                        product.setBuyer(buyer);
                    }

                    product.setSeller(seller);
                    product.setCategories(categories);

                    return product;
                })
                .forEach(this.productRepository::save);
    }

    @Override
    public void exportProductsInPriceRangeWithNoBuyer() throws IOException {
        Set<Product> products = this.productRepository.findByPriceBetweenAndBuyerIsNullOrderByPrice(
                BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        List<ProductInPriceRangeDto> dtos = products.stream().map(p -> {
            ProductInPriceRangeDto dto = modelMapper.map(p, ProductInPriceRangeDto.class);

            dto.setSeller(String.format("%s %s", p.getSeller().getFirstName(),
                    p.getSeller().getLastName()));

            return dto;
        }).collect(Collectors.toList());

        Writer writer = new FileWriter("src/main/resources/out/products-in-range.json");

        this.gson.toJson(dtos, writer);

        writer.flush();
        writer.close();
    }
}
