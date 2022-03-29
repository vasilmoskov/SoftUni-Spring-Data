package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostDto;
import softuni.exam.instagraphlite.models.dto.PostsSeedRootDto;
import softuni.exam.instagraphlite.models.entities.PictureEntity;
import softuni.exam.instagraphlite.models.entities.PostEntity;
import softuni.exam.instagraphlite.models.entities.UserEntity;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final Path path = Path.of("src", "main", "resources", "files", "posts.xml");

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PostsSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        PostsSeedRootDto rootDto = (PostsSeedRootDto) unmarshaller.unmarshal(new File(path.toAbsolutePath().toString()));

        List<String> result = new ArrayList<>();

        for (PostDto dto : rootDto.getPosts()) {
            Optional<PostEntity> postOpt = this.postRepository.findByCaption(dto.getCaption());
            Optional<UserEntity> userOpt = this.userRepository.findByUsername(dto.getUser().getUsername());
            Optional<PictureEntity> picOpt = this.pictureRepository.findByPath(dto.getPicture().getPath());

            if (this.validationUtil.isValid(dto) && postOpt.isEmpty() && userOpt.isPresent() && picOpt.isPresent()) {
                PostEntity postEntity = new PostEntity()
                        .setCaption(dto.getCaption())
                        .setUser(userOpt.get())
                        .setPicture(picOpt.get());

                this.postRepository.save(postEntity);
                result.add("Successfully imported Post, made by " + dto.getUser().getUsername());
            } else {
                result.add("Invalid Post");
            }
        }

        return String.join("\n", result);
    }
}
