package com.example.springdataintroexercise;

import com.example.springdataintroexercise.task1.repositories.AuthorRepository;
import com.example.springdataintroexercise.task1.repositories.BookRepository;
import com.example.springdataintroexercise.task1.repositories.CategoryRepository;
import com.example.springdataintroexercise.task1.services.AuthorService;
import com.example.springdataintroexercise.task1.services.BookService;
import com.example.springdataintroexercise.task1.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    private void seedDatabase() throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }

    @Override
    public void run(String... args) throws Exception {
//        seedDatabase();

//        List<Book> booksAfterYear2000 = this.bookService.getBooksAfterYear2000();
//
//        for (Book book : booksAfterYear2000) {
//            System.out.println(book.getReleaseDate());
//        }

//        List<Book> allBooksByGeorgePowell = this.bookService.getAllBooksByGeorgePowell();
//                for (Book book : allBooksByGeorgePowell) {
//            System.out.println(book.getTitle());
//        }

//        Set<Author> allAuthorsWithAtLeastOneBookBefore1990 = this.authorService.getAllAuthorsWithAtLeastOneBookBefore1990();
//
//        for (Author author : allAuthorsWithAtLeastOneBookBefore1990) {
//            System.out.println(author.getFirstName() + " " + author.getLastName());
//        }

//        List<Author> allAuthorsOrderedByBooksCountDescending = this.authorService.getAllAuthorsOrderedByBooksCountDescending();
//
//        for (Author author : allAuthorsOrderedByBooksCountDescending) {
//            System.out.println(author.getFirstName() + " " + author.getLastName());
//        }

//        this.bookService.printAllBooksTitlesWithAgeRestriction("tEen");
//        this.bookService.printGoldenEditionBooksTitlesWithLessThan5000Copies();
//        this.bookService.printTitlesAndPricesOfBooksWithPricesLessThan5AndHigherThan40();
//        this.bookService.printTitlesOfBooksNotReleasedInYear(1998);
//        this.bookService.printBooksReleasedBefore("30-12-1989");

//        this.authorService.printAuthorNamesEndingWith("dy");

//        this.bookService.printTitlesContaining("WOR");
//        this.bookService.printTitlesOfBooksWhoseAuthorsLastNameStartsWith("unt");

//        this.bookService.printNumberOfBooksWhoseTitleIsLongerThan(40);

//        this.bookService.printNumberOfBookCopiesByAuthorsFirstAndLastName("Randy", "Graham");

//        this.bookService.printInfoByBookTitle("Things Fall Apart");

//        this.bookService.printTotalAmountOfBookCopiesAdded("12 Oct 2005", 100);
//        this.bookService.printTotalAmountOfBookCopiesAdded( 44,"06 Jun 2013") ;

//        this.bookService.printCountOfRemoveBooksWithCopiesLowerThan(290);

        this.bookService.printBooksCountWrittenByAuthor("Roger", "Porter");
    }
}
