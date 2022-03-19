package bg.softuni.json_processing_exercise.product_shop.repositories;

import bg.softuni.json_processing_exercise.product_shop.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal greaterThan, BigDecimal lowerThan);
}
