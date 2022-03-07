package com.example.springdataintroexercise;

import com.example.springdataintroexercise.task2.entities.Town;
import com.example.springdataintroexercise.task2.entities.User;
import com.example.springdataintroexercise.task2.repositories.TownRepository;
import com.example.springdataintroexercise.task2.repositories.UserRepository;
import com.example.springdataintroexercise.task2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ConsoleRunnerForUsers implements CommandLineRunner {
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TownRepository townRepository;


    public ConsoleRunnerForUsers(UserService userService) {
        this.userService = userService;
    }


    private void insertUsers() throws IOException {
        User vasko = new User();

        vasko.setUsername("vmoskov");
        vasko.setPassword("Vasko1!");
        vasko.setEmail("vasko_moskov@abv.bg");
        vasko.setRegisteredOn(LocalDateTime.of(2020, 12, 1, 12, 30, 45, 50));
        vasko.setLastTimeLoggedIn(LocalDateTime.of(2021, 1, 1, 12, 30, 45, 50));
        vasko.setAge(24);
        vasko.setDeleted(false);

        Town plovdiv = new Town();
        plovdiv.setName("Plovdiv");
        plovdiv.setCountry("Bulgaria");

        townRepository.save(plovdiv);

        vasko.setTown(plovdiv);
        vasko.setFirstName("Vasil");
        vasko.setLastName("Moskov");

        userRepository.save(vasko);
    }

    private void insertInvalidUsers() throws IOException {
        User vasko = new User();

        vasko.setUsername("y");
        vasko.setPassword("s");
        vasko.setEmail("vasko_moskov@yahaosqwo.com");
        vasko.setRegisteredOn(LocalDateTime.of(2020, 12, 1, 12, 30, 45, 50));
        vasko.setLastTimeLoggedIn(LocalDateTime.of(2021, 1, 1, 12, 30, 45, 50));
        vasko.setAge(24);
        vasko.setDeleted(false);

//        Town plovdiv = new Town();
//        plovdiv.setName("Plovdiv");
//        plovdiv.setCountry("Bulgaria");
//
//        townRepository.save(plovdiv);

//        vasko.setTown(plovdiv);
        vasko.setFirstName("Todor");
        vasko.setLastName("Moskov");

        userRepository.save(vasko);
    }

    @Override
    public void run(String... args) throws Exception {
//        insertInvalidUsers();

//        userService.printAllUsernamesAndEmailsWithGivenEmailProvider("opera");

//        userService.removeInactiveSince(LocalDateTime.of(2022, 1,1,1,1));
    }
}
