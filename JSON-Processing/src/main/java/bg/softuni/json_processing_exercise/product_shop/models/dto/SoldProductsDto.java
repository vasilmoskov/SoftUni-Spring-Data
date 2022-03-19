package bg.softuni.json_processing_exercise.product_shop.models.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SoldProductsDto {
    @Expose
    private Integer count;
    @Expose
    private List<SoldProductDto2> products;

    public SoldProductsDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SoldProductDto2> getProducts() {
        return products;
    }

    public void setProducts(List<SoldProductDto2> products) {
        this.products = products;
    }
}
