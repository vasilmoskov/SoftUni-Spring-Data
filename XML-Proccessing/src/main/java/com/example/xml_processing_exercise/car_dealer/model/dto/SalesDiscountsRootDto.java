package com.example.xml_processing_exercise.car_dealer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesDiscountsRootDto {
    @XmlElement(name = "sale")
    private List<SaleDiscountDto> sales;

    public SalesDiscountsRootDto() {
    }

    public List<SaleDiscountDto> getSales() {
        return sales;
    }

    public SalesDiscountsRootDto setSales(List<SaleDiscountDto> sales) {
        this.sales = sales;
        return this;
    }
}
