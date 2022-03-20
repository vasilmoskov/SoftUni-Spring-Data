package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersRootSeedDto {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDto> suppliers;

    public SuppliersRootSeedDto() {
    }

    public List<SupplierSeedDto> getSuppliers() {
        return suppliers;
    }

    public SuppliersRootSeedDto setSuppliers(List<SupplierSeedDto> suppliers) {
        this.suppliers = suppliers;
        return this;
    }
}
