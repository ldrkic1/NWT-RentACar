package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.models.Role;
import ba.unsa.etf.notificationmicroservice.models.User;
import ba.unsa.etf.notificationmicroservice.repositories.RoleRepository;
import ba.unsa.etf.notificationmicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void save(User user) {
        Set<Role>roles=user.getRoles();
        Set<Role> newRoles = new HashSet<>();
        for(Role role: roles){
            System.out.println("**** optional: " + roleRepository.findByRoleName(role.getRoleName()));
            newRoles.add(roleRepository.findByRoleName(role.getRoleName()));
        }
        user.setRoles(newRoles);
        userRepository.save(user);
    }
    public Optional<User> getUser(String username){
        Optional<User>user=userRepository.findByUsername(username);
        if(user.isEmpty())throw new NotFoundException("User with username: "+username+" doesn't exist");
      return user;
    }
}
