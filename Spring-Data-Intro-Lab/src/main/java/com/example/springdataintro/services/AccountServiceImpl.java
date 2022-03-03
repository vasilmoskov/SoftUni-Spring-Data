package com.example.springdataintro.services;

import com.example.springdataintro.exceptions.AccountDoesNotExistException;
import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;
import com.example.springdataintro.repositories.AccountRepository;
import com.example.springdataintro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) throws AccountDoesNotExistException {
        Account account = this.accountRepository.findById(id).orElseThrow();

        checkIfAccountBelongsToUserElseThrow(account);

        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(money) > 0) {
            account.setBalance(currentBalance.subtract(money));
        }

        this.accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(NullPointerException::new);

        checkIfAccountBelongsToUserElseThrow(account);

        if (money.compareTo(new BigDecimal(0)) >= 0) {
            account.setBalance(account.getBalance().add(money));
        }

        this.accountRepository.save(account);
    }

    private void checkIfAccountBelongsToUserElseThrow(Account account) {
        this.userRepository
                .findAll()
                .stream()
                .filter(u -> u.getAccounts().contains(account))
                .findFirst()
                .orElseThrow();
    }
}
