package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedDto;
import com.example.football.models.dto.PlayersSeedRootDto;
import com.example.football.models.entity.PlayerEntity;
import com.example.football.models.entity.StatEntity;
import com.example.football.models.entity.TeamEntity;
import com.example.football.models.entity.TownEntity;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "xml", "players.xml");
        return Files.readString(path);
    }

    @Override
    public String importPlayers() throws JAXBException {
        InputStream inputStream = getClass().getResourceAsStream("/files/xml/players.xml");
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));

        JAXBContext jaxbContext = JAXBContext.newInstance(PlayersSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        PlayersSeedRootDto dtos = (PlayersSeedRootDto) unmarshaller.unmarshal(bfr);

        List<PlayerEntity> entities = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        for (PlayerSeedDto dto : dtos.getPlayers()) {
            Optional<PlayerEntity> playerOpt = this.playerRepository.findByEmail(dto.getEmail());
            boolean isValid = this.validationUtil.isValid(dto);

            if (isValid && playerOpt.isEmpty()) {
                TownEntity town = this.townRepository.findByName(dto.getTown().getName()).orElseThrow();
                TeamEntity team = this.teamRepository.findByName(dto.getTeam().getName()).orElseThrow();
                StatEntity stat = this.statRepository.findById(dto.getStat().getId()).orElseThrow();
                LocalDate birthDay = LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                PlayerEntity entity = this.modelMapper.map(dto, PlayerEntity.class)
                        .setBirthDate(birthDay)
                        .setTown(town)
                        .setTeam(team)
                        .setStat(stat);

                entities.add(entity);
                messages.add(String.format("Successfully imported Player %s %s - %s",
                        dto.getFirstName(), dto.getLastName(), dto.getPosition().name()));
            } else {
                messages.add("Invalid Player");
            }
        }

        this.playerRepository.saveAll(entities);
        return String.join("\n", messages);
    }

    @Override
    public String exportBestPlayers() {
        List<String> players = this.playerRepository.findAllByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(
                        LocalDate.of(1995, 1, 2), LocalDate.of(2002, 12, 31))
                .stream()
                .map(PlayerEntity::toString)
                .toList();

        return String.join("\n", players);
    }
}
