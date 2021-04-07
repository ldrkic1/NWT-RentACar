package ba.unsa.etf.usermicroservice.repositories;

import ba.unsa.etf.usermicroservice.RoleName;
import ba.unsa.etf.usermicroservice.models.Role;
import ba.unsa.etf.usermicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    //daj sve klijente iz baze
    @Query(value="SELECT * FROM user u, role r, users_roles ur WHERE r.role_name LIKE 'ROLE_CLIENT' AND u.id=ur.user_id AND r.id=ur.role_id", nativeQuery = true)
    List<User> getClients ();

    //daj sve usere sa nekom rolom
    @Query(value = "SELECT * FROM user u, role r, users_roles ur WHERE u.id=ur.user_id AND r.id=ur.role_id AND LOWER(r.role_name) LIKE LOWER(:role_name)", nativeQuery = true)
    List<User> getUsersByRole (@Param("role_name")String role_name);

    //je li user admin
    @Query(value="SELECT * FROM user u, role r, users_roles ur WHERE r.role_name LIKE 'ROLE_ADMIN' AND u.id=ur.user_id AND r.id=ur.role_id AND u.id=:id", nativeQuery = true)
    User isUserAdmin (@Param("id") Long id);

    //daj sve role nekog usera
    @Query("SELECT u.roles FROM User u WHERE u.id=:id")
    Set<Role> getRolesById (@Param("id")Long id);

    @Query(value="SELECT * FROM user u, role r, users_roles ur WHERE r.role_name LIKE 'ROLE_ADMIN' AND u.id=ur.user_id AND r.id=ur.role_id", nativeQuery = true)
    List<User> getAdmins();
}
