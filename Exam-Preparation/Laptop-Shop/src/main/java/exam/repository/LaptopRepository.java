package exam.repository;

import exam.model.entity.LaptopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<LaptopEntity, Long> {
    Optional<LaptopEntity> findByMacAddress(String macAddress);

    List<LaptopEntity> findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();
}
