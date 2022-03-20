package com.example.xml_processing_exercise.car_dealer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class SupplierEntity extends BaseEntity {
    private String name;
    private Boolean isImporter;
    private List<PartEntity> parts;

    public SupplierEntity() {
    }

    @Column
    public String getName() {
        return name;
    }

    public SupplierEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "is_importer")
    public Boolean getImporter() {
        return isImporter;
    }

    public SupplierEntity setImporter(Boolean importer) {
        this.isImporter = importer;
        return this;
    }

    @OneToMany(mappedBy = "supplier")
    public List<PartEntity> getParts() {
        return parts;
    }

    public SupplierEntity setParts(List<PartEntity> parts) {
        this.parts = parts;
        return this;
    }
}
