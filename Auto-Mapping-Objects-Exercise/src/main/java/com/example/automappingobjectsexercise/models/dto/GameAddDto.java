package com.example.automappingobjectsexercise.models.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameAddDto {
    private String title;
    private String trailer;
    private String thumbnailUrl;
    private Double size;
    private BigDecimal price;
    private String description;
    private String releaseDate;

    public GameAddDto() {
    }

    public GameAddDto(String title, String trailer, String thumbnailUrl, Double size,
                      BigDecimal price, String description, String releaseDate) {

        this.title = title;
        this.trailer = trailer;
        this.thumbnailUrl = thumbnailUrl;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp = "[A-Z][a-z]{3,100}", message = "Password should contain at least one uppercase letter.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Size(min = 11, max = 11)
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "^https?://.*")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Positive(message = "Size should be a positive number")
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Positive(message = "Price should be a positive number")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Size(min = 20)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

