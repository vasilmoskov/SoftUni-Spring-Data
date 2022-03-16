package com.example.automappingobjectsexercise.services.impl;

import com.example.automappingobjectsexercise.models.dto.UserLoginDao;
import com.example.automappingobjectsexercise.models.dto.UserRegisterDto;
import com.example.automappingobjectsexercise.models.entities.Game;
import com.example.automappingobjectsexercise.models.entities.Order;
import com.example.automappingobjectsexercise.models.entities.User;
import com.example.automappingobjectsexercise.repositories.GameRepository;
import com.example.automappingobjectsexercise.repositories.UserRepository;
import com.example.automappingobjectsexercise.services.UserService;
import com.example.automappingobjectsexercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public List<User> viewGames() {
        return this.userRepository.findAll();
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String email = userRegisterDto.getEmail();
        String password = userRegisterDto.getPassword();
        String confirmPassword = userRegisterDto.getConfirmPassword();
        String fullName = userRegisterDto.getFullName();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords don't match.");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.violation(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = modelMapper.map(userRegisterDto, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        this.userRepository.save(user);
    }

    @Override
    public void login(UserLoginDao userLoginDao) {
        Set<ConstraintViolation<UserLoginDao>> violations = validationUtil.violation(userLoginDao);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        String email = userLoginDao.getEmail();
        String password = userLoginDao.getPassword();

        User user = this.userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            user.setLoggedIn(true);
            loggedInUser = user;
        } else {
            System.out.println("Incorrect username / password");
        }
    }

    @Override
    public void logout() {
        if (loggedInUser == null) {
            System.out.println("Cannot log out. No user was logged in.");
        } else {
            loggedInUser.setLoggedIn(false);
            System.out.printf("User %s successfully logged out.\n", loggedInUser.getFullName());
            loggedInUser = null;
        }

        /*
        List<User> users = this.userRepository.findAll();

        for (User user : users) {
            if (user.isLoggedIn()) {
                user.setLoggedIn(false);
                System.out.printf("User %s successfully logged out.\n", user.getFullName());
                return;
            }
        }
         */

    }

    @Override
    public void addGameToShoppingCart(User user, String title) {
        Game game = this.gameRepository.findByTitle(title);

        if (user.isLoggedIn()) {
            user.getShoppingCart().add(game);
            System.out.println(title + " added to cart.");
        }
    }

    @Override
    public void removeGameFromShoppingCart(User user, String title) {
        Game game = this.gameRepository.findByTitle(title);

        if (user.isLoggedIn()) {
            user.getShoppingCart().remove(game);
            System.out.println(title + " removed from cart.");
        }
    }

    @Override
    public void buyGame(User user, Set<Game> games) {
        if (user.isLoggedIn()) {
            Set<Game> existingGames = user.getGames();

            games.removeIf(existingGames::contains);

            if (!games.isEmpty()) {
                existingGames.addAll(games);

                System.out.println("Successfully bought games:");
                games.forEach(g -> System.out.println(" -" + g.getTitle()));

                Order order = new Order();
                order.setBuyer(user);
                order.setProducts(games);
            }
        }
    }
}
