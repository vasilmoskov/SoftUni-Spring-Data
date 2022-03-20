package com.example.xml_processing_exercise.car_dealer.repository;

import com.example.xml_processing_exercise.car_dealer.model.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
