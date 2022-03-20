package com.example.xml_processing_exercise.product_shop.services.impl;

import com.example.xml_processing_exercise.product_shop.models.dto.ProductInRangeDto;
import com.example.xml_processing_exercise.product_shop.models.dto.ProductSeedDto;
import com.example.xml_processing_exercise.product_shop.models.dto.ProductsInRangeRootDto;
import com.example.xml_processing_exercise.product_shop.models.dto.ProductsSeedRootDto;
import com.example.xml_processing_exercise.product_shop.models.entities.Product;
import com.example.xml_processing_exercise.product_shop.models.entities.User;
import com.example.xml_processing_exercise.product_shop.repositories.ProductRepository;
import com.example.xml_processing_exercise.product_shop.services.CategoryService;
import com.example.xml_processing_exercise.product_shop.services.ProductService;
import com.example.xml_processing_exercise.product_shop.services.UserService;
import com.example.xml_processing_exercise.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private ProductRepository productRepository;
    private UserService userService;
    private CategoryService categoryService;

    public ProductServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil,
                              ProductRepository productRepository,
                              UserService userService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ProductsSeedRootDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ProductsSeedRootDto productsSeedRootDto = (ProductsSeedRootDto) unmarshaller
                .unmarshal(new File("src/main/resources/files/products.xml"));

        List<ProductSeedDto> productsDto = productsSeedRootDto.getProducts();

        productsDto.stream()
                .filter(validationUtil::isValid)
                .map(dto -> {
                    Product product = modelMapper.map(dto, Product.class);

                    User seller = this.userService.getRandomUser();

                    product.setSeller(seller);
                    product.setCategories(this.categoryService.getRandomCategories());

                    int randomNum = ThreadLocalRandom.current().nextInt();

                    if (randomNum % 2 == 0) {
                        User buyer = this.userService.getRandomUser();

                        if (buyer != seller) {
                            product.setBuyer(buyer);
                        }
                    }

                    return product;
                })
                .forEach(p -> this.productRepository.save(p));
    }

    @Override
    public void exportProductsInPriceRangeWithNoBuyer() throws IOException, JAXBException {
        Set<Product> products = this.productRepository
                .findByPriceBetweenAndBuyerIsNullOrderByPrice
                        (BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        List<ProductInRangeDto> productInRangeDtos = products
                .stream()
                .map(p -> {
                    ProductInRangeDto dto = modelMapper.map(p, ProductInRangeDto.class);

                    dto.setSeller(String.format("%s %s", p.getSeller().getFirstName(), p.getSeller().getLastName()));
                    return dto;
                })
                .collect(Collectors.toList());

        ProductsInRangeRootDto productsInRangeRootDto = new ProductsInRangeRootDto();

        productsInRangeRootDto.setProductsDto(productInRangeDtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(ProductsInRangeRootDto.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(productsInRangeRootDto, new File("src/main/resources/files/out/products-in-range.xml"));
    }
}
