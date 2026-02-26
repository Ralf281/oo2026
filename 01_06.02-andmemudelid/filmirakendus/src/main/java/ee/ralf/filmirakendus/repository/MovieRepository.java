package ee.ralf.filmirakendus.repository;

import ee.ralf.filmirakendus.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
