package exam.model.dto;

import com.google.gson.annotations.Expose;

public class JsonNameDto {
    @Expose
    private String name;

    public JsonNameDto() {
    }

    public String getName() {
        return name;
    }

    public JsonNameDto setName(String name) {
        this.name = name;
        return this;
    }
}
