package bg.softuni.json_processing_exercise.car_dealer.service;

import java.io.IOException;

public interface CarService {
    void exportCarsWithMake(String make) throws IOException;

    void exportCarsAndParts() throws IOException;
}
