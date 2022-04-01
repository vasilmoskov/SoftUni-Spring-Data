package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PlaneSeedDto;
import softuni.exam.models.dto.PlanesSeedRootDto;
import softuni.exam.models.entities.PlaneEntity;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "planes.xml");

    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(this.path);
    }

    @Override
    public String importPlanes() throws JAXBException {
        PlanesSeedRootDto rootDto = xmlParser
                .parse(PlanesSeedRootDto.class, this.path.toAbsolutePath().toString());

        List<String> messages = new ArrayList<>();

        for (PlaneSeedDto dto : rootDto.getPlanes()) {
            boolean isValid = this.validationUtil.isValid(dto);
            boolean isImported = this.planeRepository.findByAirline(dto.getAirline()).isPresent();

            if (isValid && !isImported) {
                PlaneEntity planeEntity = this.modelMapper.map(dto, PlaneEntity.class);
                this.planeRepository.save(planeEntity);
                messages.add(String.format("Successfully imported Plane %s", dto.getRegisterNumber()));
            } else {
                messages.add("Invalid Plane");
            }
        }

        return String.join("\n", messages);
    }
}
