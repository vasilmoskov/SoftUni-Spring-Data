package com.example.xml_processing_exercise.product_shop.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductsSeedRootDto {

    @XmlElement(name = "product")
    private List<ProductSeedDto> products;

    public ProductsSeedRootDto() {
    }

    public List<ProductSeedDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSeedDto> products) {
        this.products = products;
    }
}
