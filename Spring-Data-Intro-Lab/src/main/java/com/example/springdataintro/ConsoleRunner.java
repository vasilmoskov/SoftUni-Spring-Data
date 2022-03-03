package com.example.springdataintro;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;
import com.example.springdataintro.services.AccountService;
import com.example.springdataintro.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Yordan", 25);

        userService.registerUser(user);

        Account account = new Account(new BigDecimal("55000"));
        account.setUser(user);

        user.setAccounts(new HashSet<>() {{
            add(account);
        }});

        userService.addAccount(account, 1);

        accountService.withdrawMoney(new BigDecimal("50000"), 6L);
        accountService.transferMoney(new BigDecimal("30000"), 6L);

    }
}
