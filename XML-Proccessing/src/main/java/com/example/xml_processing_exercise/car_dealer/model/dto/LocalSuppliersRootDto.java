package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSuppliersRootDto {
    @XmlElement(name = "supplier")
    private List<LocalSupplierDto> suppliers;

    public LocalSuppliersRootDto() {
    }

    public List<LocalSupplierDto> getSuppliers() {
        return suppliers;
    }

    public LocalSuppliersRootDto setSuppliers(List<LocalSupplierDto> suppliers) {
        this.suppliers = suppliers;
        return this;
    }
}
