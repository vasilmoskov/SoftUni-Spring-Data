package com.example.springdataintroexercise.task2.services;

import com.example.springdataintroexercise.task2.entities.User;
import com.example.springdataintroexercise.task2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void printAllUsernamesAndEmailsWithGivenEmailProvider(String emailProvider) {
        List<User> users = this.userRepository.findAllByEmailContaining(emailProvider);

        if (users.isEmpty()) {
            System.out.println("No users found with email domain " + emailProvider);
            return;
        }

        for (User user : users) {
            System.out.printf("%s - %s\n", user.getUsername(), user.getEmail());
        }
    }

    @Override
    public void removeInactiveSince(LocalDateTime localDateTime) {
        List<User> users = this.userRepository.findAllByLastTimeLoggedInBefore(localDateTime);

        int deletedCount = users.size();

        for (User user : users) {
            user.setDeleted(true);
            this.userRepository.delete(user);
        }

        System.out.println("Deleted count: + " + deletedCount);
    }
}
