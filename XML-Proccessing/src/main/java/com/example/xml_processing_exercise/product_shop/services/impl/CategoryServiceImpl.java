package com.example.xml_processing_exercise.product_shop.services.impl;

import com.example.xml_processing_exercise.product_shop.models.dto.CategoriesSeedRootDto;
import com.example.xml_processing_exercise.product_shop.models.dto.CategorySeedDto;
import com.example.xml_processing_exercise.product_shop.models.entities.Category;
import com.example.xml_processing_exercise.product_shop.repositories.CategoryRepository;
import com.example.xml_processing_exercise.product_shop.services.CategoryService;
import com.example.xml_processing_exercise.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        if (categoryRepository.count() > 0) {
            return;
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(CategoriesSeedRootDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        CategoriesSeedRootDto categoriesSeedRootDto = (CategoriesSeedRootDto) unmarshaller
                .unmarshal(new FileReader("src/main/resources/files/categories.xml"));

        List<CategorySeedDto> categorySeedDtos = categoriesSeedRootDto.getCategories();

        categorySeedDtos.stream()
                .filter(validationUtil::isValid)
                .map(dto -> modelMapper.map(dto, Category.class))
                .forEach(c -> this.categoryRepository.save(c));
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

    }
}
