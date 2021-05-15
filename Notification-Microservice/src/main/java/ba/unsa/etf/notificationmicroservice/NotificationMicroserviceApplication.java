package ba.unsa.etf.notificationmicroservice;

import ba.unsa.etf.notificationmicroservice.DTO.QuestionDTO;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.models.*;
import ba.unsa.etf.notificationmicroservice.repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableEurekaClient
@SpringBootApplication
public class NotificationMicroserviceApplication {

    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionNotificationRepository questionNotificationRepository;

    public static void main(String[] args) {
        SpringApplication.run(NotificationMicroserviceApplication.class, args);
    }


    List<QuestionDTO> getQuestionFromClientCareService() throws JsonProcessingException {
       List<QuestionDTO>questionDTOList=new ArrayList<>();
        try {
            System.out.println("********* TRY *******");

            String resourceURL = "http://clientcare-service/question/all";
            ResponseEntity<String> response = restTemplate.getForEntity(resourceURL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            System.out.println("********* FROM SERVICE *******");
            System.out.println(root);
            /*question = new Question();
            question.setId(root.path("id").asLong());
            question.setTitle(root.path("title").asText());
            question.setQuestionNotification(null);*/

            root.forEach(q->{
                System.out.println("u foreach+++++++++");
                System.out.println(q.path("id").asLong()+" "+q.path("title"));
                Question question=new Question();
                question.setId(q.path("id").asLong());
                question.setTitle(q.path("title").asText());
                question.setQuestionNotification(null);

                User user=new User();
                user.setId(q.path("user").path("id").asLong());
                user.setFirstName(q.path("user").path("firstName").asText());
                user.setLastName(q.path("user").path("lastName").asText());
                user.setUsername(q.path("user").path("username").asText());
                System.out.println("userov id: "+q.path("user").path("id").asLong()+" "+q.path("user").path("username").asText());

                JsonNode rootRoles = q.path("user").path("roles");
                Set<Role> roleSet = new HashSet<>();
                rootRoles.forEach(role -> {
                    System.out.println("roleeee"+role.path("id").asLong() + " " + role.path("roleName").asText());
                    roleSet.add(new Role(role.path("id").asInt(), role.path("roleName").asText().equals("ROLE_CLIENT") ? RoleName.ROLE_CLIENT : RoleName.ROLE_ADMIN));
                });

                user.setRoles(roleSet);
                questionDTOList.add(new QuestionDTO(question, user));
                userRepository.save(user);
                //questionRepository.save(question);
                QuestionNotification qn1 = new QuestionNotification("Title", "Content", LocalDateTime.now(), user, question);
                questionNotificationRepository.save(qn1);

            });

        } catch (HttpClientErrorException exception) {
            //throw new ValidationException(exception.getLocalizedMessage());
            System.out.println("***** Exception");
            JSONObject json = new JSONObject(exception.getResponseBodyAsString());
            throw new NotFoundException(json.get("message").toString());
        } catch (IllegalStateException exception) {
            System.out.println("---------------------------------------------------");
            System.out.println("Izuzetak");
            System.out.println(exception.getMessage());
            throw exception;
        } catch (ResourceAccessException exception) {
            System.out.println("---------------------------------------------------");
            System.out.println("Izuzetak");
            System.out.println(exception.getMessage());
            throw exception;
        }
        return questionDTOList;
    }


  @Bean
    CommandLineRunner commandLineRunner(QuestionRepository questionRepository, NotificationRepository notificationRepository,
                                        UserRepository userRepository, QuestionNotificationRepository questionNotificationRepository,
                                        ReservationRepository reservationRepository, ReservationNotificationRepository reservationNotificationRepository, RoleRepository roleRepository) {
        return (args) -> {
  /*
            Role adminRole = new Role();
            adminRole.setRoleName(RoleName.ROLE_ADMIN);
            Role clientRole = new Role();
            clientRole.setRoleName(RoleName.ROLE_CLIENT);
            Set<Role> clientRoles = new HashSet<Role>();
            clientRoles.add(clientRole);
            //roleRepository.saveAll(clientRoles);
            Set<Role> adminRoles = new HashSet<Role>();
            adminRoles.add(adminRole);
            Set<Role> adminClientRoles = new HashSet<Role>();
            adminClientRoles.add(adminRole);
            adminClientRoles.add(clientRole);
            //users
            User klijent1 = new User("idedic2", "Irma", "Dedic");
            klijent1.setRoles(clientRoles);

            User klijent2 = new User("neko", "Neko", "Nekic");
            klijent2.setRoles(clientRoles);
            User admin = new User("admin", "Admin", "Adminic");
            admin.setRoles(adminRoles);
            User klijentAdmin = new User("haso2", "Haso", "Hasic");
            klijentAdmin.setRoles(adminClientRoles);
            userRepository.saveAll(List.of(klijent1, klijent2, admin, klijentAdmin));
            //questions

            //klijent1=userRepository.findByUsername("idedic2").get();
            QuestionNotification qn1 = new QuestionNotification("Title", "Content", LocalDateTime.now(), null, null);
            Question question1 = new Question();
            question1.setTitle("Osiguranje automobila");
            question1.setQuestionNotification(qn1);
            qn1.setQuestion(question1);
            //klijent1.setNotificationSet(Set.of(qn1));
            //questionRepository.save(question1);
            klijent1=userRepository.findByUsername(klijent1.getUsername()).get() ;
            qn1.setUser(klijent1);

            QuestionNotification qn2 = new QuestionNotification("Title", "Content", LocalDateTime.of(2021, Month.MARCH, 30, 20, 30, 40), null, null);
            Question question2 = new Question();
            question2.setTitle("Osiguranje automobilaa");
            question2.setQuestionNotification(qn2);
            qn2.setQuestion(question2);
            klijentAdmin=userRepository.findByUsername(klijentAdmin.getUsername()).get() ;
            qn2.setUser(klijentAdmin);
            questionNotificationRepository.saveAll(List.of(qn1, qn2));

            ReservationNotification rn1 = new ReservationNotification("Title", "Content", LocalDateTime.of(2021, Month.JANUARY, 29, 19, 30, 40), null, null);
            Reservation reservation1 = new Reservation();
            reservation1.setReservationNotification(rn1);
            rn1.setReservation(reservation1);
            klijent2=userRepository.findByUsername(klijent2.getUsername()).get() ;
            rn1.setUser(klijent2);

            ReservationNotification rn2 = new ReservationNotification("Title", "Content", LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40), null, null);
            Reservation reservation2 = new Reservation();
            reservation2.setReservationNotification(rn2);
            rn2.setReservation(reservation2);
            klijentAdmin=userRepository.findByUsername(klijentAdmin.getUsername()).get() ;
            rn2.setUser(klijentAdmin);
            reservationNotificationRepository.saveAll(List.of(rn1, rn2));

*/
            getQuestionFromClientCareService();
            //System.out.println("u mainu:"+proba.getId()+" "+proba.getTitle()+" ");
        };
    }
}
