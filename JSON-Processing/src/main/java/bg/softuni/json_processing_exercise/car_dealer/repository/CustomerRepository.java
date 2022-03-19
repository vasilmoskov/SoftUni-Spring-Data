package bg.softuni.json_processing_exercise.car_dealer.repository;

import bg.softuni.json_processing_exercise.car_dealer.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

//    @Query("select c from CustomerEntity c order by c.birthDate, c.isYoungDriver")
    List<CustomerEntity> findAllByOrderByBirthDateAsc();

//    @Query("select c from CustomerEntity c join c.purchases as pu join " +
//     "pu.car as ca join ca.parts as p order by sum(p.price) desc, count(pu.car) Desc ";

    @Query("select c from CustomerEntity c where c.purchases.size > 0")
    List<CustomerEntity> findAllByBoughtCar();

}
