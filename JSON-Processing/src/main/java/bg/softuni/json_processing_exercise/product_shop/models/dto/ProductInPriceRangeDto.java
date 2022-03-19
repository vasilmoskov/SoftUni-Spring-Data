package bg.softuni.json_processing_exercise.product_shop.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductInPriceRangeDto {
    @Expose
    String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String seller;

    public ProductInPriceRangeDto() {
    }

    public ProductInPriceRangeDto(String name, BigDecimal price, String seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
