package com.example.xml_processing_exercise.product_shop.repositories;

import com.example.xml_processing_exercise.product_shop.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u join Product p on u.id = p.seller.id " +
//            "where p.buyer.id > 1 " +
//            "group by u.id " +
//            "order by u.lastName, u.firstName")

    @Query("select u from  User u " +
            "where (select count(p) from Product p " +
            "where p.seller.id = u.id) > 0 " +
            "order by u.lastName, u.firstName")
    List<User> findByAtLeastOneSoldProductWhichHaveABuyerOrderByLastNameFirstName();

    @Query("select u from  User u " +
            "where (select count(p) from Product p " +
            "where p.seller.id = u.id and p.buyer is not null) > 0 " +
            "order by u.soldProducts.size desc ,u.lastName")
    List<User> findByAtLeastOneSoldProductOrderBySoldProductsDescLastName();
}
