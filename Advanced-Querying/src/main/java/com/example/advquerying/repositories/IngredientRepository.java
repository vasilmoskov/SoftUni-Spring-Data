package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByNameStartingWith(String string);

    List<Ingredient> findIngredientsByNameInOrderByPrice(Collection<String> names);

    Set<Ingredient> findIngredientsByNameIn(Set<String> names);

    @Transactional
    @Modifying
    @Query(value = "delete from Ingredient as i where i.name = :name")
    void deleteByName(@Param(value = "name")String name);

    @Transactional
    @Modifying
    @Query(value = "update Ingredient as i " +
            "set i.price = i.price * 1.1")
    void updatePrice();

    @Transactional
    @Modifying
    @Query(value = "update Ingredient as i " +
            "set i.price = i.price * 1.1 " +
            "where i.name in :names")
    void updatePriceByName(@Param(value = "names") List<String> names);
}
