package com.example.xml_processing_exercise.car_dealer.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CarService {
    void exportCarsWithMake(String make) throws IOException, JAXBException;

    void exportCarsAndParts() throws IOException, JAXBException;
}
