package com.example.springdataintro.services;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;
import com.example.springdataintro.repositories.AccountRepository;
import com.example.springdataintro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerUser(User user) {
        boolean userExists = this.userRepository.findAll().contains(user);

        if (!userExists) {
            this.userRepository.save(user);
        }
    }

    @Override
    public void addAccount(Account account, long id) {
        User user = this.userRepository.findById(id).orElseThrow();

        account.setUser(user);
        this.accountRepository.save(account);
    }


}
