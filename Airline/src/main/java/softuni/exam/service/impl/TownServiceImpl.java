package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedDto;
import softuni.exam.models.entities.TownEntity;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");
        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {
        String townsJson = readTownsFileContent();
        TownSeedDto[] dtos = this.gson.fromJson(townsJson, TownSeedDto[].class);

        List<String> messages = new ArrayList<>();

        for (TownSeedDto dto : dtos) {
            boolean isImported = this.townRepository.findByName(dto.getName()).isPresent();

            if (this.validationUtil.isValid(dto) && !isImported) {
                TownEntity entity = this.modelMapper.map(dto, TownEntity.class);
                this.townRepository.save(entity);
                messages.add(String.format("Successfully imported Town %s - %d", dto.getName(), dto.getPopulation()));
            } else {
                messages.add("Invalid Town");
            }
        }

        return String.join("\n", messages);
    }
}
