package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.PlaneEntity;

import java.util.Optional;

@Repository
public interface PlaneRepository extends JpaRepository<PlaneEntity, Long> {
    Optional<PlaneEntity> findByAirline(String airline);

    Optional<PlaneEntity> findByRegisterNumber(String registerNumber);
}
