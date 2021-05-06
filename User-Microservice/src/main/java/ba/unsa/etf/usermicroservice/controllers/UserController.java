package ba.unsa.etf.usermicroservice.controllers;

import ba.unsa.etf.usermicroservice.RoleName;
import ba.unsa.etf.usermicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.usermicroservice.exceptions.ValidationException;
import ba.unsa.etf.usermicroservice.models.Role;
import ba.unsa.etf.usermicroservice.models.User;
import ba.unsa.etf.usermicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/clients")
    public List<User> getClients(){
        return userService.getAllClients();
    }

    @GetMapping("/admins")
    public List<User> getAdmins(){
        return userService.getAllAdmins();
    }

    @PostMapping("/newUser")
    public User addUser(@RequestBody @Valid User user, Errors errors){
        user.setLastActivity(LocalDateTime.now());
        //handleUserErrors(user, errors);
        return userService.saveUser(user);
    }

    /*@GetMapping("/users/updateLastActivityTime/{id}")
    public User updateLastActivityTime(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        User notOptionalUser = user.get();
        notOptionalUser.setLastActivity(LocalDateTime.now());
        return userService.updateUser(notOptionalUser);
    }*/

    @GetMapping("/user")
    public Optional<User> findUserById(@RequestParam(value = "id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/byUsername")
    public Optional<User> findUserByUsername(@RequestParam(value = "username") String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/userDTO")
    public Optional<User> findUserDTOByUsername(@RequestParam(value = "username") String username){
        System.out.println("u user ruti saaaam");
        return userService.getUserDTO(username);
    }

    @GetMapping("/client")
    public Optional<User> findClientByUsername(@RequestParam(value = "username") String username){
        return userService.getClientByUsername(username);
    }

    @GetMapping("/admin")
    public Optional<User> findAdminByUsername(@RequestParam(value = "username") String username){
        return userService.getAdminByUsername(username);
    }
    @DeleteMapping
    public String deleteUserById(@RequestParam(value = "id") Long id){
        return userService.deleteUserById(id);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody @Valid User user, Errors errors) {
        handleUserErrors(user, errors);
        return userService.updateUser(user);
    }

    @GetMapping("/byRole")
    public List<User> getUsersByRole(@RequestParam(value = "role_name") String roleName) {
        return userService.getAllUsersWithRole(roleName);
    }
    @GetMapping("/roles")
    public Set<Role> getUsersRoles(@RequestParam(value = "id") Long id){
        return userService.getRolesForUser(id);
    }

    @GetMapping(value="/countUsers")
    public Long numberOfUsers(){
        return userService.numberOfUsers();}

    @GetMapping(value="/countClients")
    public Long numberOfClients(){
        return userService.numberOfClients();}

    private void handleUserErrors(User user, Errors errors) {
        if (errors.hasErrors()) {
            String errorsMessage = "";
            for (int i = 0; i < errors.getAllErrors().size(); i++) {
                if (errors.getAllErrors().get(i).toString().contains("firstName") && user.getFirstName().isEmpty()) {
                    errorsMessage += "Empty first name.";
                    break;
                }

            /*if(!user.getFirstName().isEmpty())
            {
                errorsMessage += "Not valid first name.";
                break;
            }*/

                if (errors.getAllErrors().get(i).toString().contains("lastName") && user.getLastName().isEmpty()) {
                    errorsMessage += "Empty last name.";
                    break;
                }
            /*if(!user.getLastName().isEmpty())
            {
                errorsMessage += "Not valid last name.";
                break;
            }*/

                if (errors.getAllErrors().get(i).toString().contains("Email") && user.getEmail().isEmpty()) {
                    errorsMessage += "Empty email.";
                    break;
                }
                if (errors.getAllErrors().get(i).toString().contains("Email") && !user.getEmail().isEmpty()) {
                    errorsMessage += "Not valid email.";
                    break;
                }

                if (user.getUsername().isEmpty()) {
                    errorsMessage += "Empty username.";
                    break;
                }
                if (errors.getAllErrors().get(i).toString().contains("username") && !user.getUsername().isEmpty()) {
                    errorsMessage += "Username size must be between 1 and 15 characters.";
                    break;
                }
                if (user.getPassword().isEmpty()) {
                    errorsMessage += "Empty password.";
                    break;
                }
                if (errors.getAllErrors().get(i).toString().contains("password")) {
                    errorsMessage += "Password not in accordance with password policy.";
                    break;
                }

            }
            throw new ValidationException(errorsMessage);
        }
    }
}

