package exam.repository;

import exam.model.entity.ShopEntity;
import exam.model.entity.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    Optional<ShopEntity> findByName(String name);
}
