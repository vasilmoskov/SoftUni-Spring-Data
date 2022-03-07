package com.example.springdataintroexercise.task1.services.impl;

import com.example.springdataintroexercise.task1.entities.Author;
import com.example.springdataintroexercise.task1.entities.Book;
import com.example.springdataintroexercise.task1.repositories.AuthorRepository;
import com.example.springdataintroexercise.task1.repositories.BookRepository;
import com.example.springdataintroexercise.task1.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String RESOURCE_PATH = "src/main/resources/files/";
    private static final String AUTHORS_FILE_NAME = "authors.txt";
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(RESOURCE_PATH + AUTHORS_FILE_NAME))
                .forEach(row -> {
                    String[] data = row.split("\\s+");
                    String firstName = data[0];
                    String lastName = data[1];

                    Author author = new Author();
                    author.setFirstName(firstName);
                    author.setLastName(lastName);

                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        List<Author> authors = this.authorRepository.findAll();

        Random random = new Random();

        return authors.get(random.nextInt(authors.size()));
    }

    @Override
    public Set<Author> getAllAuthorsWithAtLeastOneBookBefore1990() {
        Set<Author> authors = new HashSet<>();

        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.of(1990, 1, 1));

        books.forEach(b -> authors.add(b.getAuthor()));

        return authors;
    }

    @Override
    public List<Author> getAllAuthorsOrderedByBooksCountDescending() {
        List<Author> authors = this.authorRepository.findAll();

        authors.sort((a1, a2) -> a2.getBooks().size() - a1.getBooks().size());

        return authors;

        // alterantive:
//        return this.authorRepository.findAllByBooksSizeDesc();

    }

    @Override
    public void printAuthorNamesEndingWith(String ending) {
        this.authorRepository.findAllByFirstNameEndingWith(ending)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }
}
