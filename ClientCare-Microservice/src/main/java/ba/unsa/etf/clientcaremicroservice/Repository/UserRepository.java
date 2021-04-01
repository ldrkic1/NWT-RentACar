package ba.unsa.etf.clientcaremicroservice.Repository;

import ba.unsa.etf.clientcaremicroservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByFirstNameAndAndLastName(String firstName, String lastName);
}
