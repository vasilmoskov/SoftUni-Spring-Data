package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private ShampooRepository shampooRepository;
    private IngredientRepository ingredientRepository;

    public ConsoleRunner(ShampooRepository shampooRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Shampoo> shampoos = this.shampooRepository.findBySizeOrderById(Size.SMALL);
//
//        for (Shampoo shampoo : shampoos) {
//            System.out.printf("%s %s %.2flv.%n", shampoo.getBrand(), shampoo.getSize().name(), shampoo.getPrice());
//        }

//        List<Shampoo> bySizeOrLabelId = this.shampooRepository.findBySizeOrLabelIdOrderByPriceAsc(Size.MEDIUM, 10L);
//        for (Shampoo shampoo : bySizeOrLabelId) {
//            System.out.printf("%s %s %.2flv.%n", shampoo.getBrand(), shampoo.getSize().name(), shampoo.getPrice());
//        }

//        List<Shampoo> byPriceGreaterThanOrderByPriceDesc = this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(new BigDecimal("5"));
//
//        for (Shampoo shampoo : byPriceGreaterThanOrderByPriceDesc) {
//            System.out.printf("%s %s %.2flv.%n", shampoo.getBrand(), shampoo.getSize().name(), shampoo.getPrice());
//        }

//        this.ingredientRepository.findIngredientsByNameStartingWith("M")
//                .forEach(i -> System.out.printf("%s%n", i.getName()));

//        this.ingredientRepository.findIngredientsByNameInOrderByPrice(List.of("Lavender", "Herbs", "Apple"))
//                .forEach(i -> System.out.printf("%s%n", i.getName()));


//        System.out.println(this.shampooRepository.countByPriceLessThan(new BigDecimal("8.50")));

//
//        Set<Ingredient> ingredients = this.ingredientRepository.findIngredientsByNameIn(Set.of("Mineral-Collagen", "Berry"));
//        List<Shampoo> ingredientsIn = shampooRepository.findByIngredientsIn(ingredients);
//        ingredientsIn.forEach(s -> System.out.println(s.getBrand()));

//        this.ingredientRepository.deleteByName("Nettle");
        this.ingredientRepository.updatePriceByName(List.of("Nettle", "Herbs"));
    }
}
