package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.models.Role;
import ba.unsa.etf.notificationmicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r from Role r where r.roleName = :roleName")
    Role findByRoleName(@Param("roleName") RoleName roleName);
}
