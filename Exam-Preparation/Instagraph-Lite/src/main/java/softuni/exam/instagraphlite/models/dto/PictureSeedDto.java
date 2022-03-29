package softuni.exam.instagraphlite.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PictureSeedDto {
    @Expose
    @NotNull
    private String path;

    @Expose
    @Min(500)
    @Max(60000)
    @NotNull
    private Double size;

    public PictureSeedDto() {
    }

    public String getPath() {
        return path;
    }

    public PictureSeedDto setPath(String path) {
        this.path = path;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public PictureSeedDto setSize(Double size) {
        this.size = size;
        return this;
    }
}
