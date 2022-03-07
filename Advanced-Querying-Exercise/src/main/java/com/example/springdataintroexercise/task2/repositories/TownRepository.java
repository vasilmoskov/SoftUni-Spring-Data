package com.example.springdataintroexercise.task2.repositories;

import com.example.springdataintroexercise.task2.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}
