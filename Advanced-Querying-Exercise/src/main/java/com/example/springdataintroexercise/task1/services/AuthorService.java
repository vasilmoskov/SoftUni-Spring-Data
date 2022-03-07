package com.example.springdataintroexercise.task1.services;

import com.example.springdataintroexercise.task1.entities.Author;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface AuthorService {
    void seedAuthors() throws IOException;
    Author getRandomAuthor();

    Set<Author> getAllAuthorsWithAtLeastOneBookBefore1990();

    List<Author> getAllAuthorsOrderedByBooksCountDescending();

    void printAuthorNamesEndingWith(String ending);
}
