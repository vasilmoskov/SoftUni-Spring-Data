package com.example.automappingobjectsexercise.services.impl;

import com.example.automappingobjectsexercise.models.dto.GameAddDto;
import com.example.automappingobjectsexercise.models.dto.GameEditDto;
import com.example.automappingobjectsexercise.models.entities.Game;
import com.example.automappingobjectsexercise.models.entities.User;
import com.example.automappingobjectsexercise.repositories.GameRepository;
import com.example.automappingobjectsexercise.repositories.UserRepository;
import com.example.automappingobjectsexercise.services.GameService;
import com.example.automappingobjectsexercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(GameAddDto gameAddDto) {
//        if (user.isAdmin()) {
//            this.gameRepository.save(game);
//        }

        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.violation(gameAddDto);

        if (!violations.isEmpty()) {
            violations.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);

        this.gameRepository.save(game);
        System.out.println("Added game " + game.getTitle());
    }

    @Override
    public void edit(GameEditDto gameEditDto) {
        Game game = this.gameRepository.findById(gameEditDto.getId()).orElse(null);

        if (game == null) {
            System.out.println("No game with id " + gameEditDto.getId() + " found.");
            return;
        }

        game.setPrice(gameEditDto.getPrice());
        game.setSize(gameEditDto.getSize());

        this.gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());
    }

    @Override
    public void delete(Long id) {
        Game game = this.gameRepository.findById(id).orElse(null);

        if (game == null) {
            System.out.println("No game with id " + id + " found.");
            return;
        }

        this.gameRepository.delete(game);
        System.out.println("Deleted " + game.getTitle());
    }

    @Override
    public void printAll() {
        List<Game> games = this.gameRepository.findAll();

        for (Game game : games) {
            System.out.printf("%s %.2f\n", game.getTitle(), game.getPrice());
        }
    }

    @Override
    public void printDetailsOf(String title) {
        Game game = this.gameRepository.findByTitle(title);

        String date = game.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("Title: " + game.getTitle());
        System.out.println("Price: " + game.getPrice());
        System.out.println("Description: " + game.getDescription());
        System.out.println("Release date: " + date);
    }

    @Override
    public void printOwnedGames() {
        User user = getLoggedInUser();
        user.getGames().forEach(g -> System.out.println(g.getTitle()));
    }

    private User getLoggedInUser() {
        return this.userRepository
                .findAll()
                .stream()
                .filter(User::isLoggedIn)
                .findFirst()
                .orElse(null);
    }
}
