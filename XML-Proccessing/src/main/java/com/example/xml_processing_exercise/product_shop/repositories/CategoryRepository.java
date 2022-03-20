package com.example.xml_processing_exercise.product_shop.repositories;

import com.example.xml_processing_exercise.product_shop.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c order by c.products.size desc")
    List<Category> findAllCategoriesOrderByProductsCount();
}
