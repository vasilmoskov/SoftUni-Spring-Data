package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerSeedDto;
import softuni.exam.models.entities.PassengerEntity;
import softuni.exam.models.entities.TownEntity;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository,
                                ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "passengers.json");
        return Files.readString(path);
    }

    @Override
    public String importPassengers() throws IOException {
        String passengersJson = readPassengersFileContent();

        PassengerSeedDto[] dtos = this.gson.fromJson(passengersJson, PassengerSeedDto[].class);

        List<String> messages = new ArrayList<>();

        for (PassengerSeedDto dto : dtos) {

            Optional<TownEntity> townOpt = this.townRepository.findByName(dto.getTown());
            boolean isImported = this.passengerRepository.findByEmail(dto.getEmail()).isPresent();
            boolean isValid = this.validationUtil.isValid(dto);

            if (isValid && !isImported && townOpt.isPresent()) {
                PassengerEntity passengerEntity = this.modelMapper.map(dto, PassengerEntity.class)
                        .setTown(townOpt.get());

                this.passengerRepository.save(passengerEntity);
                messages.add(String.format("Successfully imported Passenger %s - %s", dto.getLastName(), dto.getEmail()));
            } else {
                messages.add("Invalid Passenger");
            }
        }

        return String.join("\n", messages);
    }

    @Override
    @Transactional
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        return this.passengerRepository.findAllByOrderByTicketsDescEmailAsc()
                .stream().map(PassengerEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
