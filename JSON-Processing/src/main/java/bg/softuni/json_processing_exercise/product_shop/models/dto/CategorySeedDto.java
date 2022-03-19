package bg.softuni.json_processing_exercise.product_shop.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategorySeedDto implements Serializable {
    @Expose
    private String name;

    public CategorySeedDto() {
    }

    public CategorySeedDto(String name) {
        this.name = name;
    }

    @Size(min = 3, max = 15, message = "Enter a valid size")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
