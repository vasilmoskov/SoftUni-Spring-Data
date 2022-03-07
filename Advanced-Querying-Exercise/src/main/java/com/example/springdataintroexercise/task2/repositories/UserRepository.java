package com.example.springdataintroexercise.task2.repositories;

import com.example.springdataintroexercise.task2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmailContaining(String emailProvider);

    void deleteAllByLastTimeLoggedInBefore(LocalDateTime localDateTime);

    List<User> findAllByLastTimeLoggedInBefore(LocalDateTime localDateTime);
}
