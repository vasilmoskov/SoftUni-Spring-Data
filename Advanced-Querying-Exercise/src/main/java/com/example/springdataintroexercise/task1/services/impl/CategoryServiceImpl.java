package com.example.springdataintroexercise.task1.services.impl;

import com.example.springdataintroexercise.task1.entities.Category;
import com.example.springdataintroexercise.task1.repositories.CategoryRepository;
import com.example.springdataintroexercise.task1.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String RESOURCE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.txt";
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(RESOURCE_PATH, CATEGORIES_FILE_NAME))
                .stream().filter(row -> !row.isEmpty())
                .forEach(categoryName -> {
                    Category category = new Category();
                    category.setName(categoryName);
                    categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random rand = new Random();
        List<Category> categories = this.categoryRepository.findAll();

        Set<Category> randomCategories = new HashSet<>();

        int numberOfElements = ThreadLocalRandom.current().nextInt(1,3);

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(categories.size());
            Category randomCategory = categories.get(randomIndex);
            randomCategories.add(randomCategory);
            categories.remove(randomIndex);
        }

        return randomCategories;
    }
}
