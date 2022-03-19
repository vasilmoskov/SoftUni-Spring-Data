package bg.softuni.json_processing_exercise.car_dealer.service;

import java.io.IOException;

public interface CustomerService {
    void exportOrderedCustomers() throws IOException;

    void exportCustomerTotalSales() throws IOException;
}
