package com.example.springdataintroexercise.task1.repositories;

import com.example.springdataintroexercise.task1.entities.Book;
import com.example.springdataintroexercise.task1.enums.AgeRestriction;
import com.example.springdataintroexercise.task1.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal greaterThan);

    @Query("select b from Book b where b.releaseDate not between :date1 and :date2")
    List<Book> findAllByReleaseDateIsNotBetween(@Param(value = "date1") LocalDate date1,
                                                @Param(value = "date2") LocalDate date2);

    List<Book> findAllByTitleContaining(String title);

    @Query("select b from Book b where b.author.lastName like :name%")
    List<Book> findAllByAuthorLastNameEndingWith(@Param(value = "name") String name);

    @Query("select count(b) from Book b where length(b.title) > :count")
    Integer getCountOfBooksWhoseTitleIsLongerThan(@Param(value = "count") Integer count);

    @Query("select sum(b.copies) from Book b where b.author.firstName = :first_name and b.author.lastName = :last_name")
    Integer getSumOfCopiesByAuthorFirstAndLastName(@Param(value = "first_name") String firstName,
                                                   @Param(value = "last_name") String lastName);

    @Query("select b.title, b.editionType, b.ageRestriction, b.price from Book b where b.title = :title")
    List<Object[]> getInfoByBookTitle(@Param(value = "title") String title);

    @Modifying
    @Transactional
    @Query("update Book as b set b.copies = b.copies + :book_copies where b.releaseDate > :release_date")
    Integer updateCopiesByReleaseDateAfterWith(@Param("book_copies") int bookCopies,
                                               @Param("release_date") LocalDate releaseDate);

    @Modifying
    @Transactional
    Integer deleteByCopiesLessThan(Integer copies);

    @Procedure(procedureName = "in_out_usp_total_amount_of_books_written")
    int findTotalAmountOfBooksWrittenByAuthorsFirstAndLastName(String firstName, String lastName);

    // or:

    @Query(value = "call usp_total_amount_of_books_written(:f_name, :l_name);", nativeQuery = true)
    Integer findTotalAmountOfBooksWrittenByAuthorsFirstAndLastNameNative(@Param("f_name") String firstName,
                                                                         @Param("l_name") String lastName);
}
