package bg.softuni.json_processing_exercise.car_dealer.repository;

import bg.softuni.json_processing_exercise.car_dealer.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    List<SupplierEntity> findAllByImporter(Boolean importer);
}
