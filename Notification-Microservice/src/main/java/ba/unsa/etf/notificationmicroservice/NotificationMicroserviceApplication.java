package ba.unsa.etf.notificationmicroservice;

import ba.unsa.etf.notificationmicroservice.models.*;
import ba.unsa.etf.notificationmicroservice.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class NotificationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationMicroserviceApplication.class, args);
    }
  @Bean
    CommandLineRunner commandLineRunner(QuestionRepository questionRepository, NotificationRepository notificationRepository,
                                        UserRepository userRepository, QuestionNotificationRepository questionNotificationRepository,
                                        ReservationRepository reservationRepository, ReservationNotificationRepository reservationNotificationRepository, RoleRepository roleRepository) {
        return (args) -> {

            /*User user1 = new User("");
            userRepository.saveAll(List.of(user1));

            QuestionNotification qn1 = new QuestionNotification("Title", "Content", LocalDateTime.now(), user1, null);
            Question question1 = new Question();
            question1.setQuestionNotification(qn1);
            qn1.setQuestion(question1);

            QuestionNotification qn2 = new QuestionNotification("Title", "Content", LocalDateTime.now(), user1, null);
            Question question2 = new Question();
            question2.setQuestionNotification(qn2);
            qn2.setQuestion(question2);

            questionNotificationRepository.saveAll(List.of(qn1, qn2));

            ReservationNotification rn1 = new ReservationNotification("Title", "Content", LocalDateTime.now(), user1, null);
            Reservation reservation1 = new Reservation();
            reservation1.setReservationNotification(rn1);
            rn1.setReservation(reservation1);

            ReservationNotification rn2 = new ReservationNotification("Title", "Content", LocalDateTime.now(), user1, null);
            Reservation reservation2 = new Reservation();
            reservation2.setReservationNotification(rn2);
            rn2.setReservation(reservation2);

            reservationNotificationRepository.saveAll(List.of(rn1, rn2));
*/
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

            /*Question question = new Question();
            question.setTitle("Osiguranje automobila");
            question.setQuestionNotification(null);
            questionRepository.save(question);*/

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

            /*Reservation reservation = new Reservation();
            reservation.setReservationNotification(null);
            reservationRepository.save(reservation);*/

        };
    }
}
