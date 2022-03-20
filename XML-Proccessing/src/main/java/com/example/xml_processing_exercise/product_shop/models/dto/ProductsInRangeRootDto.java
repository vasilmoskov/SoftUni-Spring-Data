package com.example.xml_processing_exercise.product_shop.models.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeRootDto {
    @XmlElement(name = "product")
    private List<ProductInRangeDto> productsDto;

    public ProductsInRangeRootDto() {
    }

    public List<ProductInRangeDto> getProductsDto() {
        return productsDto;
    }

    public void setProductsDto(List<ProductInRangeDto> productsDto) {
        this.productsDto = productsDto;
    }
}
