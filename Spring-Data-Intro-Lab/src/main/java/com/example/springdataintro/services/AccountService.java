package com.example.springdataintro.services;

import com.example.springdataintro.exceptions.AccountDoesNotExistException;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money, Long id) throws AccountDoesNotExistException;
    void transferMoney(BigDecimal money, Long id);
}
