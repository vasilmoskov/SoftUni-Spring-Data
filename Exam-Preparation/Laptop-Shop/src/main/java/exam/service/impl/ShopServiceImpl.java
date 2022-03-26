package exam.service.impl;

import exam.model.dto.ShopSeedDto;
import exam.model.dto.ShopsSeedRootDto;
import exam.model.dto.TownSeedDto;
import exam.model.entity.ShopEntity;
import exam.model.entity.TownEntity;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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
public class ShopServiceImpl implements ShopService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "shops.xml");

    private final TownRepository townRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public ShopServiceImpl(TownRepository townRepository, ShopRepository shopRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ShopsSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ShopsSeedRootDto rootDto = (ShopsSeedRootDto) unmarshaller
                .unmarshal(new File(path.toAbsolutePath().toString()));

        List<String> messages = new ArrayList<>();

        for (ShopSeedDto dto : rootDto.getShops()) {
            Optional<ShopEntity> shopOpt = this.shopRepository.findByName(dto.getName());

            if (this.validationUtil.isValid(dto) && shopOpt.isEmpty()) {
                TownEntity townEntity = this.townRepository.findByName(dto.getTown().getName()).orElseThrow();
                ShopEntity shopEntity = this.modelMapper.map(dto, ShopEntity.class)
                        .setTown(townEntity);

                this.shopRepository.save(shopEntity);
                messages.add(String.format("Successfully imported Shop %s - %f", dto.getName(), dto.getIncome()));
            } else {
                messages.add("Invalid Shop");
            }
        }

        return String.join("\n", messages);
    }
}
