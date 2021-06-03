package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.Role;
import ba.unsa.etf.notificationmicroservice.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
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
    public void getUserTest() throws Exception{
        Optional<User> user=userService.getUser("mmujic2");
        assertThat(user.get().getUsername()).isEqualTo("mmujic2");
        NotFoundException user1 = assertThrows(
                NotFoundException.class,
                ()->userService.getUser("nepostojeci")
        );
        assertThat(user1.getMessage().contains("User with username: nepostojeci doesn't exist"));
    }

    /*
    @Test
    @Transactional
    public void saveUserTest()throws Exception {
        User user=new User("novi", "Novi", "Novic");
        Role clientRole = new Role();
        clientRole.setRoleName(RoleName.ROLE_CLIENT);
        Set<Role> clientRoles = new HashSet<Role>();
        clientRoles.add(clientRole);
        user.setRoles(clientRoles);
        userService.save(user);
        Optional<User> user1=userService.getUser("novi");
        assertThat(user1.get().getUsername()).isEqualTo("novi");
        assertThat(user1.get().getRoles().size()).isEqualTo(1);
    }*/
}
