package com.example.xml_processing_exercise.product_shop.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

//    @XmlElement(name = "sold-products")
//    private SoldProductsDto soldProductsDtos;

    @XmlElement(name = "product")
    @XmlElementWrapper(name = "sold-products")
    private List<SoldProductDto> soldProductsDtos;

    public UserSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public SoldProductsDto getSoldProductsDto() {
//        return soldProductsDtos;
//    }
//
//    public void setSoldProductsDto(SoldProductsDto soldProductsDtos) {
//        this.soldProductsDtos = soldProductsDtos;
//    }


    public List<SoldProductDto> getSoldProductsDtos() {
        return soldProductsDtos;
    }

    public void setSoldProductsDtos(List<SoldProductDto> soldProductsDtos) {
        this.soldProductsDtos = soldProductsDtos;
    }
}
