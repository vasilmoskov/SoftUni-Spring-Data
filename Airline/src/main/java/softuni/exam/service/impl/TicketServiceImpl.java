package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TicketSeedDto;
import softuni.exam.models.dto.TicketsSeedRootDto;
import softuni.exam.models.entities.PassengerEntity;
import softuni.exam.models.entities.PlaneEntity;
import softuni.exam.models.entities.TicketEntity;
import softuni.exam.models.entities.TownEntity;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "tickets.xml");

    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, PlaneRepository planeRepository,
                             TownRepository townRepository, PassengerRepository passengerRepository,
                             XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importTickets() throws JAXBException {
        TicketsSeedRootDto rootDto = this.xmlParser
                .parse(TicketsSeedRootDto.class, this.path.toAbsolutePath().toString());

        List<String> messages = new ArrayList<>();

        for (TicketSeedDto dto : rootDto.getTickets()) {

            boolean isValid = this.validationUtil.isValid(dto);
            boolean isImported = this.ticketRepository.findBySerialNumber(dto.getSerialNumber()).isPresent();
            Optional<TownEntity> fromTownOpt = this.townRepository.findByName(dto.getFromTown().getName());
            Optional<TownEntity> toTownOpt = this.townRepository.findByName(dto.getToTown().getName());
            Optional<PlaneEntity> planeOpt = this.planeRepository.findByRegisterNumber(dto.getPlane().getRegisterNumber());
            Optional<PassengerEntity> passengerOpt = this.passengerRepository.findByEmail(dto.getPassenger().getEmail());

            if (isValid && !isImported && toTownOpt.isPresent() && fromTownOpt.isPresent()
                    && planeOpt.isPresent() && passengerOpt.isPresent()) {

                TicketEntity ticketEntity = this.modelMapper.map(dto, TicketEntity.class)
                        .setTakeoff(LocalDateTime.parse(dto.getTakeoff(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .setFromTown(fromTownOpt.get())
                        .setToTown(toTownOpt.get())
                        .setPlane(planeOpt.get())
                        .setPassenger(passengerOpt.get());

                this.ticketRepository.save(ticketEntity);
                messages.add(String.format("Successfully imported Ticket %s - %s", dto.getFromTown().getName(),
                        dto.getToTown().getName()));
            } else {
                messages.add("Invalid Ticket");
            }
        }

        return String.join("\n", messages);
    }
}
