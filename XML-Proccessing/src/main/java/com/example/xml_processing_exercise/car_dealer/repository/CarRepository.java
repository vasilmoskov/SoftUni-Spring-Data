package com.example.xml_processing_exercise.car_dealer.repository;

import com.example.xml_processing_exercise.car_dealer.model.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    List<CarEntity> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
