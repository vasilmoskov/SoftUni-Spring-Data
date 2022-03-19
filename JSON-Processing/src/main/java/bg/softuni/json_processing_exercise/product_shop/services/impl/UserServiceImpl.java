package bg.softuni.json_processing_exercise.product_shop.services.impl;

import bg.softuni.json_processing_exercise.product_shop.models.dto.UserWithSoldProductsDto;
import bg.softuni.json_processing_exercise.product_shop.models.entities.User;
import bg.softuni.json_processing_exercise.product_shop.models.dto.SoldProductDto2;
import bg.softuni.json_processing_exercise.product_shop.models.dto.SoldProductsDto;
import bg.softuni.json_processing_exercise.product_shop.models.dto.TotalUsersWithSoldProductsDto;
import bg.softuni.json_processing_exercise.product_shop.models.dto.UserSeedDto;
import bg.softuni.json_processing_exercise.product_shop.models.dto.UserWithSoldProductsDto2;
import bg.softuni.json_processing_exercise.product_shop.models.entities.Product;
import bg.softuni.json_processing_exercise.product_shop.repositories.UserRepository;
import bg.softuni.json_processing_exercise.product_shop.services.UserService;
import bg.softuni.json_processing_exercise.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERS_FILE_NAME = "src/main/resources/users.json";

    private final UserRepository userRepository;

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(Gson gson, ValidationUtil validationUtil, UserRepository userRepository, ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() != 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(USERS_FILE_NAME));

        UserSeedDto[] userJsonDtos = gson.fromJson(fileContent, UserSeedDto[].class);

        Arrays.stream(userJsonDtos)
                .filter(validationUtil::isValid)
                .map(userJsonDto -> modelMapper.map(userJsonDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);

        return this.userRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportUsersBySoldProducts() throws IOException {
        List<User> users = this.userRepository.findByAtLeastOneSoldProductWhichHaveABuyerOrderByLastNameFirstName();

        /*
        List<UserWithSoldProductsDto> dtos = users
                .stream()
                .map(u -> {
                    UserWithSoldProductsDto dto = modelMapper.map(User.class, UserWithSoldProductsDto.class);

                    dto.setFirstName(u.getFirstName());
                    dto.setLastName(u.getLastName());

                    Set<ProductDto> productDtos = new HashSet<>();

                    Set<Product> soldProducts = u.getSoldProducts();

                    for (Product soldProduct : soldProducts) {
                        ProductDto productDto = new ProductDto();

                        productDto.setName(soldProduct.getName());
                        productDto.setPrice(soldProduct.getPrice());
                        String firstName = soldProduct.getBuyer().getFirstName();
                        productDto.setBuyerFirstName(firstName == null ? "-" : firstName);
                        productDto.setBuyerLastName(soldProduct.getBuyer().getLastName());

                        productDtos.add(productDto);
                    }

                    dto.setProducts(productDtos);

                    return dto;
                })
                .collect(Collectors.toList());


         */
//        UserWithSoldProductsDto[] dtos = modelMapper.map(users, UserWithSoldProductsDto[].class);

        List<UserWithSoldProductsDto> dtos = users
                .stream()
                .map(u -> modelMapper.map(u, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());

        Writer writer = new FileWriter("src/main/resources/out/users-sold-products.json");

        this.gson.toJson(dtos, writer);

        writer.flush();
        writer.close();
    }

    @Override
    public void exportUsersBySoldProductsOrderedByProductsCountDesc() throws IOException {
        List<User> users = this.userRepository.findByAtLeastOneSoldProductOrderBySoldProductsDescLastName();

        List<UserWithSoldProductsDto2> userWithSoldProductsDtos = getListOfUsersWithSoldProductsDto(users);

        TotalUsersWithSoldProductsDto totalUsersWithSoldProductsDto = new TotalUsersWithSoldProductsDto();
        totalUsersWithSoldProductsDto.setUsersCount(users.size());
        totalUsersWithSoldProductsDto.setUsers(userWithSoldProductsDtos);

        Writer writer = new FileWriter("src/main/resources/out/users-and-products.json");
        this.gson.toJson(totalUsersWithSoldProductsDto, writer);
        writer.close();
    }

    private List<UserWithSoldProductsDto2> getListOfUsersWithSoldProductsDto(List<User> users) {
        return users.stream().map(u -> {
                    UserWithSoldProductsDto2 userWithSoldProductsDto = new UserWithSoldProductsDto2();

                    userWithSoldProductsDto.setFirstName(u.getFirstName());
                    userWithSoldProductsDto.setLastName(u.getLastName());
                    userWithSoldProductsDto.setAge(u.getAge());

                    Set<Product> soldProducts = u.getSoldProducts();

                    SoldProductsDto soldProductsDto = getSoldProductsDto(soldProducts);

                    userWithSoldProductsDto.setSoldProducts(soldProductsDto);

                    return userWithSoldProductsDto;
                })
                .collect(Collectors.toList());
    }

    private SoldProductsDto getSoldProductsDto(Set<Product> soldProducts) {
        SoldProductsDto soldProductsDto = new SoldProductsDto();
        soldProductsDto.setCount(soldProducts.size());

        List<SoldProductDto2> listSoldProductDto = new ArrayList<>();

        for (Product product : soldProducts) {
            SoldProductDto2 soldProduct = new SoldProductDto2();

            soldProduct.setName(product.getName());
            soldProduct.setPrice(product.getPrice());

            listSoldProductDto.add(soldProduct);
        }

        soldProductsDto.setProducts(listSoldProductDto);

        return soldProductsDto;
    }
}
