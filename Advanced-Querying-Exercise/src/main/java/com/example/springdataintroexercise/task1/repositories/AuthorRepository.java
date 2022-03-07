package com.example.springdataintroexercise.task1.repositories;

import com.example.springdataintroexercise.task1.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDesc();

    List<Author> findAllByFirstNameEndingWith(String ending);
}
