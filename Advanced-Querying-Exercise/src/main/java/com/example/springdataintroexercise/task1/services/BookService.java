package com.example.springdataintroexercise.task1.services;

import com.example.springdataintroexercise.task1.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getBooksAfterYear2000();

    List<Book> getAllBooksByGeorgePowell();

    void printAllBooksTitlesWithAgeRestriction(String ageRestriction);

    void printGoldenEditionBooksTitlesWithLessThan5000Copies();

    void printTitlesAndPricesOfBooksWithPricesLessThan5AndHigherThan40();

    void printTitlesOfBooksNotReleasedInYear(Integer year);

    void printBooksReleasedBefore(String date);

    void printTitlesContaining(String toContain);

    void printTitlesOfBooksWhoseAuthorsLastNameStartsWith(String lastName);

    void printNumberOfBooksWhoseTitleIsLongerThan(Integer count);

    void printNumberOfBookCopiesByAuthorsFirstAndLastName(String firstName, String lastName);

    void printInfoByBookTitle(String title);

    void printTotalAmountOfBookCopiesAdded (int amount, String date);

    void printCountOfRemoveBooksWithCopiesLowerThan(int amount);

    void printBooksCountWrittenByAuthor(String firstName, String lastName);

}
