package ba.unsa.etf.usermicroservice;

import ba.unsa.etf.usermicroservice.models.Role;
import ba.unsa.etf.usermicroservice.models.User;
import ba.unsa.etf.usermicroservice.repositories.RoleRepository;
import ba.unsa.etf.usermicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
public class UserMicroserviceApplication {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @Autowired
    private RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args->{

            Role adminRole = new Role();

            //adminRole.setRoleName(RoleName.ROLE1);
            adminRole.setRoleName(RoleName.ROLE_ADMIN);
            Role clientRole = new Role();
            //clientRole.setRoleName(RoleName.ROLE2);
            clientRole.setRoleName(RoleName.ROLE_CLIENT);
            Set<Role> clientRoles = new HashSet<Role>();
            clientRoles.add(clientRole);
            //roleRepository.saveAll(clientRoles);
            Set<Role> adminRoles = new HashSet<Role>();
            adminRoles.add(adminRole);
            Set<Role> adminClientRoles = new HashSet<Role>();
            adminClientRoles.add(adminRole);
            adminClientRoles.add(clientRole);



            User admin=new User("Admin", "Adminic", "admin1@gmail.com", "admin", "1PassworD1*",Boolean.TRUE, LocalDateTime.now());
            admin.setRoles(adminRoles);


            User klijent=new User("Irma", "Dedic", "idedic@gmail.com", "idedic", "1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            klijent.setRoles(clientRoles);


            User klijent2=new User("Irma", "Dedic", "idedic@gmail.com", "irma", "1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            klijent2.setRoles(adminClientRoles);

            User clientCareUser1=new User("Lamija", "Drkic", "ldrkic1@gmail.com", "ldrkic1","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            clientCareUser1.setRoles(clientRoles);

            User clientCareUser2=new User("Mujo", "Mujic", "mujo@gmail.com", "mmujic2","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            clientCareUser2.setRoles(clientRoles);

            User clientCareUser3=new User("Niko", "Nikic", "niko@gmail.com", "nnikic123","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            clientCareUser3.setRoles(adminClientRoles);

            User clientCareUser4=new User("Admin", "Admin", "admin@gmail.com", "aadmin21","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            clientCareUser4.setRoles(adminRoles);

            User reservationUser1=new User("Goran", "Gotovac", "goran@gmail.com", "goran","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            reservationUser1.setRoles(clientRoles);

            User reservationUser2=new User("Niko", "Nikic", "niko123@gmail.com", "niko123","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            reservationUser2.setRoles(clientRoles);

            User reservationUser3=new User("Mujo", "Mujic", "mmujic@gmail.com", "mujo55","1PassworD1*", Boolean.TRUE, LocalDateTime.now());
            reservationUser3.setRoles(clientRoles);

            userRepository.saveAll(List.of(admin, klijent, klijent2, clientCareUser1, clientCareUser2, clientCareUser3, clientCareUser4, reservationUser1, reservationUser2, reservationUser3));

        };
    }
}
