package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.User;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findByUsername(String username);
    @Query(value = "SELECT * from user where username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);


    @Query(value="SELECT * FROM user u, role r, users_roles ur WHERE u.id = :id AND r.role_name LIKE 'ROLE_CLIENT' AND u.id=ur.user_id AND r.id=ur.role_id", nativeQuery = true)
    User doesExistRoleName(@Param("id") Long id);

}
