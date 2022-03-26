package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.LaptopSeedDto;
import exam.model.entity.LaptopEntity;
import exam.model.entity.ShopEntity;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.RandomAccess;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final Path path = Path.of("src", "main", "resources", "files", "json", "laptops.json");

    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, ModelMapper modelMapper,
                             ValidationUtil validationUtil, Gson gson) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importLaptops() throws IOException {
        String laptopsJson = readLaptopsFileContent();

        List<String> messages = new ArrayList<>();

        LaptopSeedDto[] dtos = this.gson.fromJson(laptopsJson, LaptopSeedDto[].class);

        for (LaptopSeedDto dto : dtos) {
            Optional<LaptopEntity> laptopOpt = this.laptopRepository.findByMacAddress(dto.getMacAddress());

            if (validationUtil.isValid(dto) && laptopOpt.isEmpty()) {
                ShopEntity shopEntity = this.shopRepository.findByName(dto.getShop().getName()).orElseThrow();

                LaptopEntity laptopEntity = this.modelMapper.map(dto, LaptopEntity.class)
                        .setShop(shopEntity);

                this.laptopRepository.save(laptopEntity);

                messages.add(String.format("Successfully imported Laptop %s - %s - %s - %s", dto.getMacAddress(),
                        dto.getCpuSpeed(), dto.getRam(), dto.getStorage()));
            } else {
                messages.add("Invalid Laptop");
            }

        }

        return String.join("\n", messages);
    }

    @Override
    public String exportBestLaptops() {
        return this.laptopRepository
                .findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc()
                .stream()
                .map(LaptopEntity::toString)
                .collect(Collectors.joining("\n\n"));
    }
}








