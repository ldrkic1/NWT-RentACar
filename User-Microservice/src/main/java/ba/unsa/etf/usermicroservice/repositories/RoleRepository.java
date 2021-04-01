package ba.unsa.etf.usermicroservice.repositories;

import ba.unsa.etf.usermicroservice.RoleName;
import ba.unsa.etf.usermicroservice.models.Role;
import ba.unsa.etf.usermicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);

    @Query(value = "SELECT * from role r where r.role_name LIKE :role_name", nativeQuery = true)
    Optional<Role> getRole (@Param("role_name") String role_name);
}
