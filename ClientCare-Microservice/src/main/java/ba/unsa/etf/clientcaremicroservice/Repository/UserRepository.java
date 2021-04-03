package ba.unsa.etf.clientcaremicroservice.Repository;

import ba.unsa.etf.clientcaremicroservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByFirstNameAndAndLastName(String firstName, String lastName);
    @Query("select u from User u where u.username=:username")
    Optional<User> getUserByUsername(String username);
}
