package ee.ralf.kontrolltoo1.repository;

import ee.ralf.kontrolltoo1.entity.Speed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeedRepository extends JpaRepository<Speed, Long> {
}
