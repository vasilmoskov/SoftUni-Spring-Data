package bg.softuni.json_processing_exercise.car_dealer.model.dto;

import com.google.gson.annotations.Expose;

public class SuppliersSeedDto {
    @Expose
    private String name;

    @Expose
    private Boolean isImporter;

    public SuppliersSeedDto() {
    }

    public String getName() {
        return name;
    }

    public SuppliersSeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean isImporter() {
        return isImporter;
    }

    public SuppliersSeedDto setImporter(Boolean importer) {
        isImporter = importer;
        return this;
    }
}
