package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureSeedDto;
import softuni.exam.instagraphlite.models.entities.PictureEntity;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "pictures.json");
        return Files.readString(path);
    }

    @Override
    public String importPictures() throws IOException {
        String picturesJson = readFromFileContent();

        List<String> result = new ArrayList<>();

        PictureSeedDto[] dtos = this.gson.fromJson(picturesJson, PictureSeedDto[].class);

        for (PictureSeedDto dto : dtos) {
            Optional<PictureEntity> picOpt = this.pictureRepository
                    .findByPath(dto.getPath());

            if (validationUtil.isValid(dto) && picOpt.isEmpty()) {
                PictureEntity pictureEntity = this.modelMapper.map(dto, PictureEntity.class);
                this.pictureRepository.save(pictureEntity);
                result.add(String.format("Successfully imported Picture, with size %.2f", dto.getSize()));
            } else {
                result.add("Invalid Picture");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportPictures() {
        return this.pictureRepository.
                findAllBySizeGreaterThanOrderBySizeAsc(30000d)
                .stream()
                .map(PictureEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
