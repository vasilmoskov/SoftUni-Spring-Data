package com.example.automappingobjectsexercise.services;

import com.example.automappingobjectsexercise.models.dto.GameAddDto;
import com.example.automappingobjectsexercise.models.dto.GameEditDto;

public interface GameService {
    void delete(Long id);

    void printAll();

    void printDetailsOf(String title);

    void printOwnedGames();

    void add(GameAddDto gameAddDto);

    void edit(GameEditDto gameEditDto);
}
