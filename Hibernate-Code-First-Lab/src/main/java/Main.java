import entities.Bike;
import entities.Car;
import entities.Company;
import entities.Driver;
import entities.Plane;
import entities.PlateNumber;
import entities.Truck;
import entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("vehicles");

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Vehicle kolelo = new Bike("Balkanche", new BigDecimal("5"), "vuzduh");
        em.persist(kolelo);

        PlateNumber plateNumber = new PlateNumber("PB2756HM");
        em.persist(plateNumber);

        Driver d1 = new Driver("Stefan Ivanov");
        em.persist(d1);

        Driver d2 = new Driver("Georgi Petrov");
        em.persist(d2);

        Set<Driver> drivers = new HashSet<>();
        drivers.add(d1);
        drivers.add(d2);

        Vehicle avensis = new Car("Avensis", new BigDecimal("7700"), "benzin-gaz", 5, plateNumber, drivers);
        em.persist(avensis);

        Company company = new Company("Ryanair");
        em.persist(company);

        Vehicle plane = new Plane("Boing", new BigDecimal("100000"), "kerosin", 90, company);
        em.persist(plane);

        Vehicle truck = new Truck("TIR", new BigDecimal("30000"), "diesel", 10);
        em.persist(truck);

        Vehicle vehicle = em.find(Vehicle.class, 2L);
        System.out.println(vehicle.getModel());

        em.getTransaction().commit();
    }
}
