package ba.unsa.etf.usermicroservice.services;


import ba.unsa.etf.usermicroservice.RoleName;
import ba.unsa.etf.usermicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.usermicroservice.exceptions.NotFoundException;
import ba.unsa.etf.usermicroservice.exceptions.ValidationException;
import ba.unsa.etf.usermicroservice.models.Role;
import ba.unsa.etf.usermicroservice.models.User;
import ba.unsa.etf.usermicroservice.repositories.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    UserService userService;


    @Test
    public void getUsersTest(){
        assertThat(userService.getAllUsers().size()).isNotEqualTo(0);
    }
    @Test
    public void getClientsTest(){
        assertThat(userService.getAllClients().size()).isNotEqualTo(0);
    }

    @Test
    public void getUserByUsernameTest() {
        Exception exception = assertThrows(NotFoundException.class, () -> userService.getUserByUsername("nepostojeci"));
        assertTrue(exception.getMessage().contains("User with username: nepostojeci doesn't exist."));

        assertThat(userService.getUserByUsername("irma").get().getUsername()).isEqualTo("irma");

    }

    @Test
    public void getUserByIdTest(){
        Exception exception = assertThrows(NotFoundException.class, () -> userService.getUserById(990L));
        assertTrue(exception.getMessage().contains("User with id: 990 doesn't exist."));

        assertThat(userService.getUserById(1L).get().getId()).isEqualTo(1L);
    }

    @Test
    public void getAllUsersWithRoleTest(){
        Exception exception = assertThrows(NotFoundException.class, () -> userService.getAllUsersWithRole("nepostojeca"));
        assertTrue(exception.getMessage().contains("Role with role name: nepostojeca doesn't exist."));

        assertThat(userService.getAllUsersWithRole("ROLE_CLIENT").size()).isNotEqualTo(0);
    }

    @Test
    public void getRolesForUserTest(){
        Exception exception = assertThrows(NotFoundException.class, () -> userService.getRolesForUser(990L));
        assertTrue(exception.getMessage().contains("User with id: 990 doesn't exist."));

        assertThat(userService.getRolesForUser(1L).size()).isNotEqualTo(0);
    }

    @Test
    public void deleteUserByIdTest(){
        Exception exception = assertThrows(NotFoundException.class, () -> userService.deleteUserById(990L));
        assertTrue(exception.getMessage().contains("User with id: 990 doesn't exist."));

        exception = assertThrows(ApiRequestException.class, () -> userService.deleteUserById(1L));
        assertTrue(exception.getMessage().contains("User with id: 1 can't be removed."));

        assertThat(userService.deleteUserById(3L)).isEqualTo("User with id: 3 successfully removed.");
    }


    @Test
    public void saveUserTest(){
        Role clientRole = new Role();
        //clientRole.setRoleName(RoleName.ROLE2);
        clientRole.setRoleName(RoleName.ROLE_CLIENT);
        Set<Role> clientRoles = new HashSet<Role>();
        clientRoles.add(clientRole);

        User user=new User();
        user.setFirstName("");
        user.setLastName("Dedic");
        user.setEmail("idedic@gmail.com");
        user.setUsername("irma22");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        Exception exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("First name is empty"));

        user.setFirstName("Irma222");
        user.setLastName("Dedic");
        user.setEmail("idedic@gmail.com");
        user.setUsername("irma22");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("First name must contain letters and spaces"));

        user.setFirstName("Irma");
        user.setLastName("");
        user.setEmail("idedic@gmail.com");
        user.setUsername("irma22");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Last name is empty"));

        user.setFirstName("Irma");
        user.setLastName("Dedic****");
        user.setEmail("idedic@gmail.com");
        user.setUsername("irma22");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Last name must contain letters and spaces"));

        user.setFirstName("Irma");
        user.setLastName("Dedic");
        user.setEmail("");
        user.setUsername("irma22");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Email is empty"));

        user.setFirstName("Irma");
        user.setLastName("Dedic");
        user.setEmail("irmaa");
        user.setUsername("irma22");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Not valid email"));


        user.setFirstName("Irma");
        user.setLastName("Dedic");
        user.setEmail("irma@gmail.com");
        user.setUsername("");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Username is empty"));

        user.setFirstName("Irma");
        user.setLastName("Dedic");
        user.setEmail("irma@gmail.com");
        user.setUsername("idedic78023458jfdsm888");
        user.setPassword("1PassworD1");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Username size must be between 1 and 15 characters"));

        user.setFirstName("Irma");
        user.setLastName("Dedic");
        user.setEmail("irma@gmail.com");
        user.setUsername("idedic1510");
        user.setPassword("");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Password is empty"));

        user.setFirstName("Irma");
        user.setLastName("Dedic");
        user.setEmail("irma@gmail.com");
        user.setUsername("idedic1510");
        user.setPassword("irma");
        user.setEnabled(true);
        user.setLastActivity(LocalDateTime.now());
        user.setRoles(clientRoles);
        exception = assertThrows(ValidationException.class, () -> userService.saveUser(user));
        assertTrue(exception.getMessage().contains("Password not in accordance with password policy."));


    }
}
