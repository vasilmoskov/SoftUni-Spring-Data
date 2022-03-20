package com.example.xml_processing_exercise.product_shop.services.impl;


import com.example.xml_processing_exercise.product_shop.models.dto.SoldProductDto;
//import com.example.xml_processing_exercise.product_shop.models.dto.SoldProductsDto;
import com.example.xml_processing_exercise.product_shop.models.dto.UserSoldProductsDto;
import com.example.xml_processing_exercise.product_shop.models.dto.UsersSeedRootDto;
import com.example.xml_processing_exercise.product_shop.models.dto.UsersSoldProductsRootDto;
import com.example.xml_processing_exercise.product_shop.models.entities.Product;
import com.example.xml_processing_exercise.product_shop.models.entities.User;
import com.example.xml_processing_exercise.product_shop.repositories.UserRepository;
import com.example.xml_processing_exercise.product_shop.services.UserService;
import com.example.xml_processing_exercise.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private ValidationUtil validationUtil;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers() throws JAXBException, FileNotFoundException {
        if (this.userRepository.count() > 0) {
            return;
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(UsersSeedRootDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        UsersSeedRootDto dto = (UsersSeedRootDto) unmarshaller
                .unmarshal(new FileReader("src/main/resources/files/users.xml"));

//        Alternative:
//        InputStream inputStream = getClass().getResourceAsStream("/files/users.xml");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        UsersSeedDto dto = (UsersSeedDto) unmarshaller.unmarshal(bufferedReader);

        dto.getUserSeedDtos()
                .stream()
                .filter(userSeedDto -> validationUtil.isValid(userSeedDto))
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(u -> userRepository.save(u));
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);

        return this.userRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportUsersBySoldProducts() throws IOException, JAXBException {
        List<User> users = this.userRepository
                .findByAtLeastOneSoldProductWhichHaveABuyerOrderByLastNameFirstName();

        UsersSoldProductsRootDto usersSoldProductsRootDto = new UsersSoldProductsRootDto();

        List<UserSoldProductsDto> userSoldProductsDto = users
                .stream()
                .map(p -> {
                    UserSoldProductsDto dto = modelMapper.map(p, UserSoldProductsDto.class);

                    Set<Product> soldProducts = p.getSoldProducts();

                    List<SoldProductDto> listOfSoldProductsDto = soldProducts.stream().map(sp -> {
                        SoldProductDto soldProductsDto = modelMapper.map(sp, SoldProductDto.class);

                        if (sp.getBuyer() != null) {
                            soldProductsDto.setBuyerFirstName(sp.getBuyer().getFirstName());
                            soldProductsDto.setBuyerLastName(sp.getBuyer().getLastName());
                        }

                        return soldProductsDto;
                    }).collect(Collectors.toList());

                    dto.setSoldProductsDtos(listOfSoldProductsDto);
                    return dto;
                })
                .collect(Collectors.toList());

        usersSoldProductsRootDto.setUserSoldProductsDtos(userSoldProductsDto);

        JAXBContext jaxbContext = JAXBContext.newInstance(UsersSoldProductsRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usersSoldProductsRootDto, new File("src/main/resources/files/out/users-sold-products.xml"));
    }

    @Override
    public void exportUsersBySoldProductsOrderedByProductsCountDesc() throws IOException {

    }
}