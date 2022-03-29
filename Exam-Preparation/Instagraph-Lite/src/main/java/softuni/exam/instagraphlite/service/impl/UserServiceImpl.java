package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDto;
import softuni.exam.instagraphlite.models.entities.PictureEntity;
import softuni.exam.instagraphlite.models.entities.UserEntity;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository,
                           ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "users.json");
        return Files.readString(path);
    }

    @Override
    public String importUsers() throws IOException {
        String usersJson = readFromFileContent();

        List<String> result = new ArrayList<>();

        UserSeedDto[] dtos = this.gson.fromJson(usersJson, UserSeedDto[].class);

        for (UserSeedDto dto : dtos) {

            Optional<UserEntity> userOpt = this.userRepository.findByUsername(dto.getUsername());
            Optional<PictureEntity> picOpt = this.pictureRepository.findByPath(dto.getProfilePicture());

            if (validationUtil.isValid(dto) && userOpt.isEmpty() && picOpt.isPresent()) {

                UserEntity userEntity = this.modelMapper.map(dto, UserEntity.class)
                        .setProfilePicture(picOpt.get());

                this.userRepository.save(userEntity);
                result.add("Successfully imported User: " + dto.getUsername());
            } else {
                result.add("Invalid User");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return this.userRepository
                .findAllByOrderByCountPostsDescIdAsc()
                .stream().map(UserEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
