package bg.softuni.json_processing_exercise.car_dealer;

import bg.softuni.json_processing_exercise.car_dealer.service.CarService;
import bg.softuni.json_processing_exercise.car_dealer.service.CustomerService;
import bg.softuni.json_processing_exercise.car_dealer.service.SaleService;
import bg.softuni.json_processing_exercise.car_dealer.service.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final DbInitializer dbInitializer;
    private final CustomerService customerService;
    private final CarService carService;
    private final SupplierService supplierService;
    private final SaleService saleService;

    public ConsoleRunner(DbInitializer dbInitializer, CustomerService customerService, CarService carService, SupplierService supplierService, SaleService saleService) {
        this.dbInitializer = dbInitializer;
        this.customerService = customerService;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.dbInitializer.seedDb();
        this.customerService.exportOrderedCustomers();
        this.carService.exportCarsWithMake("Toyota");
        this.supplierService.exportNonImporters();
        this.carService.exportCarsAndParts();
        this.customerService.exportCustomerTotalSales();
        this.saleService.exportSalesDiscounts();
    }
}
