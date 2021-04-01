package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Model.Role;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void getUserByIdTest() {
        Optional<User> user = userService.getUserById(1L);
        Optional<User> user1 = userService.getUserById(9L);

        assertTrue(user.isPresent());
        assertFalse(user1.isPresent());
    }

}