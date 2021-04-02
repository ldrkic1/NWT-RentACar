package ba.unsa.etf.vehiclemicroservice.demo.Repository;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Category findByDescription(String description);
}
