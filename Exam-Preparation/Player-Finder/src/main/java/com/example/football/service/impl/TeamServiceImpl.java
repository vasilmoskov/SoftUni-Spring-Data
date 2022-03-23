package com.example.football.service.impl;

import com.example.football.models.dto.TeamsSeedDto;
import com.example.football.models.entity.TeamEntity;
import com.example.football.models.entity.TownEntity;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TeamServiceImpl(TeamRepository townRepository, TownRepository townRepository1, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.teamRepository = townRepository;
        this.townRepository = townRepository1;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "teams.json");
        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importTeams() throws IOException {
        String fileContent = readTeamsFileContent();

        TeamsSeedDto[] teamsSeedDto = this.gson.fromJson(fileContent, TeamsSeedDto[].class);

        List<TeamEntity> teamEntities = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        for (TeamsSeedDto t : teamsSeedDto) {
            if (validationUtil.isValid(t) && !alreadyExists(t.getName())) {
                TownEntity townEntity = this.townRepository.findByName(t.getTownName()).orElseThrow();
                TeamEntity teamEntity = this.modelMapper.map(t, TeamEntity.class)
                        .setTown(townEntity);
                teamEntities.add(teamEntity);
                messages.add(String.format("Successfully imported Team %s - %d", t.getName(), t.getFanBase()));
            } else {
                messages.add("Invalid Team");
            }
        }

        this.teamRepository.saveAll(teamEntities);
        return String.join("\n", messages);
    }

    private boolean alreadyExists(String teamName) {
        return this.teamRepository.findByName(teamName).isPresent();
    }

}
