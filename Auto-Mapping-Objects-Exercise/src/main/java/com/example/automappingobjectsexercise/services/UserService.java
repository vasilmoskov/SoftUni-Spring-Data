package com.example.automappingobjectsexercise.services;

import com.example.automappingobjectsexercise.models.dto.UserLoginDao;
import com.example.automappingobjectsexercise.models.dto.UserRegisterDto;
import com.example.automappingobjectsexercise.models.entities.Game;
import com.example.automappingobjectsexercise.models.entities.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> viewGames();

    void logout();

    void addGameToShoppingCart(User user, String title);

    void removeGameFromShoppingCart(User user, String title);

    void buyGame(User user, Set<Game> games);

    void register(UserRegisterDto userRegisterDto);

    void login(UserLoginDao userLoginDao);

}
