package ee.ralf.autorakendus.repository;

import ee.ralf.autorakendus.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByBrandAndModelAndYear(String brand, String model, Integer year);
}
