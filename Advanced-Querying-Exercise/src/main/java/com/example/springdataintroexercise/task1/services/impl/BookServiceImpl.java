package com.example.springdataintroexercise.task1.services.impl;

import com.example.springdataintroexercise.task1.enums.AgeRestriction;
import com.example.springdataintroexercise.task1.enums.EditionType;
import com.example.springdataintroexercise.task1.entities.Author;
import com.example.springdataintroexercise.task1.entities.Book;
import com.example.springdataintroexercise.task1.entities.Category;
import com.example.springdataintroexercise.task1.repositories.BookRepository;
import com.example.springdataintroexercise.task1.services.AuthorService;
import com.example.springdataintroexercise.task1.services.BookService;
import com.example.springdataintroexercise.task1.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private static final String RESOURCE_PATH = "/Users/vasilmoskov/IdeaProjects/spring-data-intro-exercise/src/main/resources/files/";
    private static final String BOOKS_FILE_NAME = "books.txt";

    private BookRepository bookRepository;
    private AuthorService authorService;
    private CategoryService categoryService;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH + BOOKS_FILE_NAME))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = authorService.getRandomAuthor();

                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

                    LocalDate releaseDate = LocalDate.parse(data[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));

                    int copies = Integer.parseInt(data[2]);

                    BigDecimal price = new BigDecimal(data[3]);

                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];

                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));

                    Set<Category> categories = categoryService.getRandomCategories();

                    Book book = new Book(title, editionType, price, releaseDate, ageRestriction,
                            author, categories, copies);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> getBooksAfterYear2000() {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31));
    }

    @Override
    public List<Book> getAllBooksByGeorgePowell() {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George", "Powell");
    }

    @Override
    public void printAllBooksTitlesWithAgeRestriction(String ageRestrictionInput) {

        try {
            AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRestrictionInput.toUpperCase());

            this.bookRepository.findAllByAgeRestriction(ageRestriction)
                    .forEach(b -> System.out.println(b.getTitle()));
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid age restriction.");
        }
    }

    @Override
    public void printGoldenEditionBooksTitlesWithLessThan5000Copies() {
        this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printTitlesAndPricesOfBooksWithPricesLessThan5AndHigherThan40() {
        this.bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(
                        BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(b -> System.out.printf("%s - $%.2f\n", b.getTitle(), b.getPrice()));
    }

    @Override
    public void printTitlesOfBooksNotReleasedInYear(Integer year) {
        this.bookRepository.findAllByReleaseDateIsNotBetween(
                        LocalDate.of(year, 1, 1),
                        LocalDate.of(year, 12, 31))
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printBooksReleasedBefore(String date) {
        String[] dateParts = date.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        this.bookRepository.findAllByReleaseDateBefore(LocalDate.of(year, month, day))
                .forEach(b -> System.out.printf("%s %s %.2f\n",
                        b.getTitle(), b.getEditionType().name(), b.getPrice()));
    }

    @Override
    public void printTitlesContaining(String toContain) {
        this.bookRepository.findAllByTitleContaining(toContain)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printTitlesOfBooksWhoseAuthorsLastNameStartsWith(String lastName) {
        this.bookRepository.findAllByAuthorLastNameEndingWith(lastName)
                .forEach(b -> System.out.printf("%s (%s %s)\n",
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    @Override
    public void printNumberOfBooksWhoseTitleIsLongerThan(Integer count) {
        Integer numberOfBooks = this.bookRepository
                .getCountOfBooksWhoseTitleIsLongerThan(count);

        System.out.printf("There are %d books with longer title than %d symbols\n",
                numberOfBooks, count);
    }

    @Override
    public void printNumberOfBookCopiesByAuthorsFirstAndLastName(String firstName, String lastName) {
        Integer countCopies = this.bookRepository.getSumOfCopiesByAuthorFirstAndLastName(firstName, lastName);

        System.out.printf("%s %s - %d\n", firstName, lastName, countCopies);
    }

    @Override
    public void printInfoByBookTitle(String title) {
        List<Object[]> bookInfo = this.bookRepository.getInfoByBookTitle(title);

        for (Object[] objects : bookInfo) {
            String bookTitle = (String) objects[0];
            EditionType editionType = (EditionType) objects[1];
            AgeRestriction ageRestriction = (AgeRestriction) objects[2];
            BigDecimal price = (BigDecimal) objects[3];

            System.out.printf("%s %s %s %.2f\n", bookTitle, editionType.name(), ageRestriction.name(), price);
        }
    }

    @Override
    public void printTotalAmountOfBookCopiesAdded(int amount, String date) {
        date = date.replace(" ", "-");
        LocalDate releaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

        Integer updated = this.bookRepository.updateCopiesByReleaseDateAfterWith(amount, releaseDate);

        int total = updated * amount;

        System.out.printf("%d books are released after %s, so total of %d book copies were added\n",
                updated, date, total);
    }

    @Override
    public void printCountOfRemoveBooksWithCopiesLowerThan(int amount) {
        Integer count = this.bookRepository.deleteByCopiesLessThan(amount);

        System.out.println(count + " books deleted");
    }

    @Override
    public void printBooksCountWrittenByAuthor(String firstName, String lastName) {
        int count = this.bookRepository.findTotalAmountOfBooksWrittenByAuthorsFirstAndLastName(firstName, lastName);

        System.out.printf("%s %s has written %d books\n", firstName, lastName, count);
    }
}
