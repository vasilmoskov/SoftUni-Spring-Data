package bg.softuni.json_processing_exercise.car_dealer.repository;

import bg.softuni.json_processing_exercise.car_dealer.model.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
