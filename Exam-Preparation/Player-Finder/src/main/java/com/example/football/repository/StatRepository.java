package com.example.football.repository;

import com.example.football.models.entity.StatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, Long> {
    Optional<StatEntity> findByShootingAndPassingAndEndurance(Double shooting, Double passing, Double endurance);
}
