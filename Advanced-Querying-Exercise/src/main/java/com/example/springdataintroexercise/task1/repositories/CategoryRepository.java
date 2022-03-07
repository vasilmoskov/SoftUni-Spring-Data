package com.example.springdataintroexercise.task1.repositories;

import com.example.springdataintroexercise.task1.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
