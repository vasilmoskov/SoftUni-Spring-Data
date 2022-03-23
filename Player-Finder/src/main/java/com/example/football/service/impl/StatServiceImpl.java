package com.example.football.service.impl;

import com.example.football.models.dto.StatSeedDto;
import com.example.football.models.dto.StatsSeedRootDto;
import com.example.football.models.entity.StatEntity;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "xml", "stats.xml");
        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importStats() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(StatsSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        InputStream inputStream = getClass().getResourceAsStream("/files/xml/stats.xml");
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
        StatsSeedRootDto statsSeedRootDto = (StatsSeedRootDto) unmarshaller.unmarshal(bfr);

        List<StatEntity> statEntities = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        List<StatSeedDto> statsDto = statsSeedRootDto.getStats();

        for (StatSeedDto statDto : statsDto) {
            Optional<StatEntity> statOpt = this.statRepository.findByShootingAndPassingAndEndurance(
                    statDto.getShooting(), statDto.getPassing(), statDto.getEndurance());

            if (validationUtil.isValid(statDto) && statOpt.isEmpty()) {
                StatEntity statEntity = this.modelMapper.map(statDto, StatEntity.class);
                statEntities.add(statEntity);
                messages.add(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                        statDto.getShooting(), statDto.getPassing(), statDto.getEndurance()));
            } else {
                messages.add("Invalid Stat");
            }
        }

        this.statRepository.saveAll(statEntities);
        return String.join("\n", messages);
    }
}
