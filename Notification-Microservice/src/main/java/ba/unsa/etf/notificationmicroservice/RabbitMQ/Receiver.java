package ba.unsa.etf.notificationmicroservice.RabbitMQ;

import ba.unsa.etf.notificationmicroservice.DTO.QuestionDTO;
import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.models.Role;
import ba.unsa.etf.notificationmicroservice.models.User;
import ba.unsa.etf.notificationmicroservice.repositories.QuestionNotificationRepository;
import ba.unsa.etf.notificationmicroservice.repositories.QuestionRepository;
import ba.unsa.etf.notificationmicroservice.repositories.RoleRepository;
import ba.unsa.etf.notificationmicroservice.repositories.UserRepository;
import ba.unsa.etf.notificationmicroservice.services.QuestionNotificationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

@Component
public class Receiver {

    @Autowired
    QuestionNotificationService questionNotificationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionNotificationRepository questionNotificationRepository;

    @RabbitListener(queues = Config.QUEUE)
    public void consumeMessageFromQueue(QuestionDTO questionDTO) {
        System.out.println("Message recieved from queue : " + questionDTO.getQuestion().getId());
        System.out.println("Message recieved from queue : " + questionDTO.getUser().getUsername());

        User user;
        if (!userRepository.findByUsername(questionDTO.getUser().getUsername()).isPresent()) {
            user = new User();
            user.setId(questionDTO.getUser().getId());
            user.setFirstName(questionDTO.getUser().getFirstName());
            user.setLastName(questionDTO.getUser().getLastName());
            user.setUsername(questionDTO.getUser().getUsername());
            Role roleClient = roleRepository.findByRoleName(RoleName.ROLE_CLIENT);
            System.out.println("************");
            System.out.println(roleClient.getRoleName());
            System.out.println("************");
            Set<Role> clientRoles = new HashSet<Role>();
            clientRoles.add(roleClient);
            user.setRoles(clientRoles);
            userRepository.save(user);
        } else {
            user = userRepository.findByUsername(questionDTO.getUser().getUsername()).get();
        }

        QuestionNotification questionNotification=new QuestionNotification(questionDTO.getQuestion().getTitle(), questionDTO.getUser().getUsername()+" je postavio pitanje: "+questionDTO.getQuestion().getTitle(), LocalDateTime.now(), null, null);
        Question question2 = new Question();
        question2.setId(questionDTO.getQuestion().getId());
        question2.setTitle(questionDTO.getQuestion().getTitle());
        question2.setQuestionNotification(questionNotification);
        questionNotification.setQuestion(question2);
        questionNotification.setUser(user);
        questionNotificationRepository.save(questionNotification);
    }

}
