package ee.ralf.decathlon.repository;

import ee.ralf.decathlon.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}