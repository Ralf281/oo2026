package ee.ralf.proovikontrolltoo.repository;

import ee.ralf.proovikontrolltoo.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,Long> {
}
