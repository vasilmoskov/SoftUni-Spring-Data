package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.PassengerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
    Optional<PassengerEntity> findByEmail(String email);

    @Query("select p from PassengerEntity p order by p.tickets.size desc, p.email asc")
    List<PassengerEntity> findAllByOrderByTicketsDescEmailAsc();
}
