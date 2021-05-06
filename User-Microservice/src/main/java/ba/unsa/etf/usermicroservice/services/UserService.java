package ba.unsa.etf.usermicroservice.services;

import ba.unsa.etf.usermicroservice.RoleName;
import ba.unsa.etf.usermicroservice.exceptions.ApiException;
import ba.unsa.etf.usermicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.usermicroservice.exceptions.NotFoundException;
import ba.unsa.etf.usermicroservice.exceptions.ValidationException;
import ba.unsa.etf.usermicroservice.models.Role;
import ba.unsa.etf.usermicroservice.models.User;
import ba.unsa.etf.usermicroservice.repositories.RoleRepository;
import ba.unsa.etf.usermicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    //Pattern p = Pattern.compile("^[ A-Za-z]+$");
    Pattern name=Pattern.compile("^[-a-zA-Z-()]+(\\s+[-a-zA-Z-()]+)*$");
    Pattern mail=Pattern.compile("^(.+)@(.+)$");
    Pattern password=Pattern.compile("^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)" + "(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$");

    public List<User> getAllUsers(){
        if(userRepository.findAll().isEmpty())
            throw new NotFoundException("There is no users");
        return userRepository.findAll();
    }
    public List<User> getAllClients(){
        List<User>clients=new ArrayList<>();
        clients=userRepository.getClients();
        if(clients.isEmpty())throw new NotFoundException("There is no clients");
        return clients;
    }
    public List<User> getAllAdmins(){
        List<User>admins=new ArrayList<>();
        admins=userRepository.getAdmins();
        if(admins.isEmpty())throw new NotFoundException("There is no admins");
        return admins;
    }
    private Boolean passwordTest(String password){
        Boolean lowerLetter=false;
        Boolean upperLetter=false;
        Boolean digit=false;
        for(int i=0;i<password.length();i++){
            if(password.charAt(i)>='A' && password.charAt(i)<='Z')
                upperLetter=true;
            if(password.charAt(i)>='a' && password.charAt(i)<='z')
                lowerLetter=true;
            if(password.charAt(i)>='0' && password.charAt(i)<='9')
                digit=true;

        }
        if(lowerLetter && upperLetter && digit && password.length()>=8)
            return true;
        return false;
    }

    public User saveUser(User user){
        if(user.getFirstName().isEmpty())
            throw new ValidationException("First name is empty");
        if(!name.matcher(user.getFirstName()).matches())
            throw new ValidationException("First name must contain letters and spaces");
        if(user.getLastName().isEmpty())
            throw new ValidationException("Last name is empty");
        if(!name.matcher(user.getLastName()).matches())
            throw new ValidationException("Last name must contain letters and spaces");
        if(user.getEmail().isEmpty())
            throw new ValidationException("Email is empty");
        if(!mail.matcher(user.getEmail()).matches())
            throw new ValidationException("Not valid email");
        if(user.getUsername().isEmpty())
            throw new ValidationException("Username is empty");
        if(user.getUsername().length()<1 || user.getUsername().length()>15)
            throw new ValidationException("Username size must be between 1 and 15 characters");
        if(user.getPassword().isEmpty())
            throw new ValidationException("Password is empty");
        //if(!password.matcher(user.getPassword()).matches())
        if(!passwordTest(user.getPassword()))
            throw new ValidationException("Password not in accordance with password policy.");
        Optional<User> userOptional=userRepository.findByUsername(user.getUsername());
        if(userOptional.isPresent()){
            throw new ApiRequestException("Username is already taken");
        }
        Set<Role>roles=user.getRoles();
        Set<Role> nove = new HashSet<>();
        for(Role role: roles){
            System.out.println(roleRepository.findByRoleName(role.getRoleName()));
            nove.add(roleRepository.findByRoleName(role.getRoleName()));
        }
        user.setRoles(nove);
        user.setLastActivity(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        Optional<User>optionalUser=userRepository.findById(id);
        if(!optionalUser.isPresent())throw new NotFoundException("User with id: "+ id+ " doesn't exist.");
        return optionalUser;

    }

    public Optional<User> getUserDTO(String username){
        System.out.println("bazaaaa: "+username);
        Optional<User>user=getUserByUsername(username);
        user.get().setEnabled(null);
        user.get().setLastActivity(LocalDateTime.of(1990, Month.MARCH, 29, 20, 30, 40));
        user.get().setEmail(null);
        user.get().setFirstName(null);
        user.get().setLastName(null);
        return user;
    }

    public Optional<User> getUserByUsername(String username) {
        Optional<User>optionalUser=userRepository.findByUsername(username);
        if(!optionalUser.isPresent())throw new NotFoundException("User with username: "+ username+ " doesn't exist.");
        return optionalUser;
    }

    public String deleteUserById(Long id) {
        Optional<User>optionalUser=userRepository.findById(id);
        if(!optionalUser.isPresent())throw new NotFoundException("User with id: "+ id+ " doesn't exist.");
        if(userRepository.isUserAdmin(id)!=null)
            throw new ApiRequestException("User with id: "+id+" can't be removed.");
        userRepository.deleteById(id);
        return "User with id: "+id+" successfully removed.";
    }

    public User updateUser(User user) {
        if(!userRepository.findById(user.getId()).isPresent())
            throw new NotFoundException("User doesn't exist");
        /*User existingUser=userRepository.getOne(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setEnabled(user.getEnabled());
        existingUser.setRoles(user.getRoles());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setLastActivity(user.getLastActivity());*/
        return userRepository.save(user);
    }

    private Boolean doesContain(User user, RoleName roleName){
        for(Role role: user.getRoles()){
            if(role.getRoleName().equals(roleName))
                return true;
        }
        return false;
    }
    public List<User> getAllUsersWithRole(String roleName) {
        if(roleRepository.getRole(roleName).isEmpty())
            throw new NotFoundException("Role with role name: "+ roleName +" doesn't exist.");
        List<User>users=new ArrayList<>();
        users=userRepository.getUsersByRole(roleName.toString());
        if(users.isEmpty())
            throw new NotFoundException("There is no users with role: "+ roleName);
        return users;
    }

    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public Set<Role> getRolesForUser(Long id){
        Set<Role>roles=new HashSet<>();
        Optional<User>optionalUser=userRepository.findById(id);
        if(!optionalUser.isPresent())throw new NotFoundException("User with id: "+ id+ " doesn't exist.");
        roles=userRepository.getRolesById(id);
        //roles.addAll(optionalUser.get().getRoles());
        return roles;
    }
    public Long numberOfUsers(){
        return Long.valueOf(getAllUsers().size());
    }
    public Long numberOfClients(){
        return Long.valueOf(getAllClients().size());
    }

    public Optional<User> getClientByUsername(String username) {
        for(User user: getAllClients()){
            if(user.getUsername().equals(username)) {
                user.setEnabled(null);
                user.setPassword(null);
                user.setLastActivity(LocalDateTime.of(1990, Month.MARCH, 29, 20, 30, 40));
                user.setEmail(null);
                return Optional.of(user);
            }
        }
        throw new NotFoundException("There is no client with username: "+username);
    }

    public Optional<User> getAdminByUsername(String username) {
        for(User user: getAllAdmins()) {
            if (user.getUsername().equals(username)) {
                user.setEnabled(null);
                user.setPassword(null);
                user.setLastActivity(LocalDateTime.of(1990, Month.MARCH, 29, 20, 30, 40));
                user.setEmail(null);
                return Optional.of(user);
            }
        }
        throw new NotFoundException("There is no admin with username: "+username);
    }


}

