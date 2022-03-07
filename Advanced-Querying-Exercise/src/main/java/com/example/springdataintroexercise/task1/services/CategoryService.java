package com.example.springdataintroexercise.task1.services;

import com.example.springdataintroexercise.task1.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> getRandomCategories();
}
