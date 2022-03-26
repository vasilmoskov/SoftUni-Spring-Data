package exam.service.impl;

import exam.model.dto.TownSeedDto;
import exam.model.dto.TownsSeedRootDto;
import exam.model.entity.TownEntity;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "towns.xml");

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TownsSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TownsSeedRootDto rootDto = (TownsSeedRootDto) unmarshaller
                .unmarshal(new File(path.toAbsolutePath().toString()));

        List<String> messages = new ArrayList<>();

        for (TownSeedDto dto : rootDto.getTowns()) {
            Optional<TownEntity> townOpt = this.townRepository.findByName(dto.getName());

            if (this.validationUtil.isValid(dto) && townOpt.isEmpty()) {
                TownEntity townEntity = this.modelMapper.map(dto, TownEntity.class);
                this.townRepository.save(townEntity);
                messages.add("Successfully imported Town " + dto.getName());
            } else {
                messages.add("Invalid Town");
            }
        }

        return String.join("\n", messages);
    }
}
