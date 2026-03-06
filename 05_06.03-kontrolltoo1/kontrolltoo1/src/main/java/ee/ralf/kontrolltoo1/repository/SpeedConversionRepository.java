package ee.ralf.kontrolltoo1.repository;

import ee.ralf.kontrolltoo1.entity.SpeedConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeedConversionRepository extends JpaRepository<SpeedConversion, Long> {
}
