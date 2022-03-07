package com.example.springdataintroexercise.task2.services;

import java.time.LocalDateTime;

public interface UserService {
    void printAllUsernamesAndEmailsWithGivenEmailProvider(String emailProvider);

    void removeInactiveSince(LocalDateTime localDateTime);
}
