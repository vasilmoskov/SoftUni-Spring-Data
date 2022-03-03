package com.example.springdataintro.repositories;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Iterable<Account> findAccountsByBalanceGreaterThan(BigDecimal balance);
    Iterable<Account> findAccountsByUser(User user);
}
