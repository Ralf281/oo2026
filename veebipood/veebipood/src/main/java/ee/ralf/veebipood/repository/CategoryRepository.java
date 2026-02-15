package ee.ralf.veebipood.repository;

import ee.ralf.veebipood.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
