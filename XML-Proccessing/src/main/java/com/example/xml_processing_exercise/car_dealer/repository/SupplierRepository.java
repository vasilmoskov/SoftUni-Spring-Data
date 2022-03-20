package com.example.xml_processing_exercise.car_dealer.repository;

import com.example.xml_processing_exercise.car_dealer.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    List<SupplierEntity> findAllByImporter(Boolean importer);
}
