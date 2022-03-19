package bg.softuni.json_processing_exercise.product_shop.services.impl;

import bg.softuni.json_processing_exercise.product_shop.models.dto.CategorySeedDto;
import bg.softuni.json_processing_exercise.product_shop.models.entities.Category;
import bg.softuni.json_processing_exercise.product_shop.services.CategoryService;
import bg.softuni.json_processing_exercise.product_shop.models.dto.CategoryByProductsCountDto;
import bg.softuni.json_processing_exercise.product_shop.models.entities.Product;
import bg.softuni.json_processing_exercise.product_shop.repositories.CategoryRepository;
import bg.softuni.json_processing_exercise.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_FILE_NAME = "src/main/resources/categories.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() != 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(CATEGORY_FILE_NAME));

        CategorySeedDto[] categorySeedDto = gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDto)
                .filter(validationUtil::isValid)
                .map(cDto -> modelMapper.map(cDto, Category.class))
                .forEach(this.categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        long catCount = ThreadLocalRandom.current().nextInt(1, 3);

        long catRepoCount = categoryRepository.count();

        for (int i = 0; i < catCount; i++) {
            long randId = ThreadLocalRandom.current().nextLong(1, catRepoCount + 1);

            Category category = categoryRepository.
                    findById(randId).orElse(null);

            categories.add(category);
        }

        return categories;
    }

    @Override
    public void exportCategoriesByProductsCount() throws IOException {
        List<Category> categories = this.categoryRepository.findAllCategoriesOrderByProductsCount();

        List<CategoryByProductsCountDto> dtos = categories.stream()
                .map(this::mapCategoryToCategoryByProductCountDto)
                .collect(Collectors.toList());

        /*
        Allternative:
        List<CategoryByProductCountDto> dtos = categories.stream()
                .map(c -> {
                    CategoryByProductCountDto dto = new CategoryByProductCountDto();

                    dto.setCategory(c.getName());
                    dto.setProductsCount(c.getProducts().size());

                    BigDecimal revenue = new BigDecimal(0);

                    for (Product product : c.getProducts()) {
                        revenue = revenue.add(product.getPrice());
                    }

                    dto.setTotalRevenue(revenue);

                    BigDecimal averagePrice = revenue.divide(new BigDecimal(c.getProducts().size()), 6, RoundingMode.HALF_UP);
                    dto.setAveragePrice(averagePrice);

                    return dto;
                })
                .collect(Collectors.toList());

         */

        Writer writer = new FileWriter("src/main/resources/out/categories-by-products-count.json");
        this.gson.toJson(dtos, writer);

        writer.close();

    }

    private CategoryByProductsCountDto mapCategoryToCategoryByProductCountDto(Category c) {
        CategoryByProductsCountDto dto = new CategoryByProductsCountDto();

        dto.setCategory(c.getName());
        dto.setProductsCount(c.getProducts().size());

        BigDecimal revenue = new BigDecimal(0);

        for (Product product : c.getProducts()) {
            revenue = revenue.add(product.getPrice());
        }

        dto.setTotalRevenue(revenue);

        BigDecimal averagePrice = revenue.divide(new BigDecimal(c.getProducts().size()), 6, RoundingMode.HALF_UP);
        dto.setAveragePrice(averagePrice);

        return dto;
    }
}
