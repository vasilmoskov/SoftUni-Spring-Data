package com.example.springdataintro.services;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;

public interface UserService {
    void registerUser(User user);

    void addAccount(Account account, long id);
}
