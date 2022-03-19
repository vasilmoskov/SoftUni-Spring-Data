package bg.softuni.json_processing_exercise.product_shop.models.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserWithSoldProductsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<SoldProductDto> products;

    public UserWithSoldProductsDto() {
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

    public Set<SoldProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<SoldProductDto> products) {
        this.products = products;
    }
}
