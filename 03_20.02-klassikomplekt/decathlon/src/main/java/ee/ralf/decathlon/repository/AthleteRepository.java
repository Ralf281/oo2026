package ee.ralf.decathlon.repository;

import ee.ralf.decathlon.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
