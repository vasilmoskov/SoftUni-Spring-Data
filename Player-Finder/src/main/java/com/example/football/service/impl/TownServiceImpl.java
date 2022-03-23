package com.example.football.service.impl;

import com.example.football.models.dto.TownsSeedDto;
import com.example.football.models.entity.TownEntity;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");
        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importTowns() throws IOException {
        String fileContent = readTownsFileContent();

        TownsSeedDto[] townsSeedDtos = this.gson.fromJson(fileContent, TownsSeedDto[].class);

        List<TownEntity> townEntities = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        for (TownsSeedDto t : townsSeedDtos) {
            if (validationUtil.isValid(t) && !alreadyExists(t.getName())) {
                TownEntity townEntity = this.modelMapper.map(t, TownEntity.class);
                townEntities.add(townEntity);
                messages.add(String.format("Successfully imported Town %s - %d", t.getName(), t.getPopulation()));
            } else {
                messages.add("Invalid town");
            }
        }

        this.townRepository.saveAll(townEntities);
        return String.join("\n", messages);
    }

    private boolean alreadyExists(String townName) {
        return this.townRepository.findByName(townName).isPresent();
    }
}
