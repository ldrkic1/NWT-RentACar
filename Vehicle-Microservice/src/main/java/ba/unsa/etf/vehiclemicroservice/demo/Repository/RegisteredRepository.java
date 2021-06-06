package ba.unsa.etf.vehiclemicroservice.demo.Repository;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredRepository extends JpaRepository<Registered,Long> {
    Optional<Registered>  findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Registered> getUserByUsername(String username);
}
