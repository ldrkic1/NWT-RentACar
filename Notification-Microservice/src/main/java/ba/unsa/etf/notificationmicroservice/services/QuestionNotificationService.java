package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.*;
import ba.unsa.etf.notificationmicroservice.repositories.NotificationRepository;
import ba.unsa.etf.notificationmicroservice.repositories.QuestionNotificationRepository;
import ba.unsa.etf.notificationmicroservice.repositories.QuestionRepository;
import ba.unsa.etf.notificationmicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ba.unsa.etf.notificationmicroservice.RoleName.ROLE_CLIENT;

@Service
public class QuestionNotificationService {
    @Autowired
    private QuestionNotificationRepository questionNotificationRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;


    //dodaj question notifikaciju
    public QuestionNotification addQuestionNotification(QuestionNotification questionNotification) {
        if(questionNotification.getTitle().isEmpty())
            throw new ValidationException("Notification title is empty");

        System.out.println("eeeeeeeeeeeee"+questionNotification.getTitle());

        Optional<User> user = userRepository.findByUsername(questionNotification.getUser().getUsername());
        System.out.println("***********************poslije findUsername"+ questionNotification.getUser().getUsername());
        System.out.println("***********************bazaaaa"+ userRepository.findAll().size());
        if (user.isPresent()) {
            //if (doesContain(user.get(), ROLE_CLIENT)) {
            if (userRepository.doesExistRoleName(user.get().getId()) != null) {
                questionNotification.setUser(user.get());

            } else
                throw new ApiRequestException(questionNotification.getUser().getFirstName() + " " + questionNotification.getUser().getLastName() + " isn't client.");
        } else {
            System.out.println("***********************client ne postoji");
            throw new NotFoundException("Client with username: " + questionNotification.getUser().getUsername() + " doesn't exist.");

        }
        //questionNotification.setUser(user.get());
        Optional<Question> question = questionRepository.findById(questionNotification.getQuestion().getId());
        System.out.println("00000000000"+questionNotification.getQuestion().getId());
        if (!question.isPresent()) {
            question = Optional.of(new Question()); // dodano
            question.get().setTitle(questionNotification.getQuestion().getTitle()); // dodano
            /*questionNotification.setQuestion(question.get());
            questionNotification.setCreatedAt(LocalDateTime.now());
            return questionNotificationRepository.save(questionNotification);*/
        }
        else{
            if(!questionRepository.findAllByQuestionId(question.get().getId()).isEmpty())
                throw new ApiRequestException("Question notification with question id: "+question.get().getId()+" already exists");
        }
            // kad se iskljuci generatedVabv
            question.get().setQuestionNotification(questionNotification);
         // dodano
        questionNotification.setQuestion(question.get()); // dodano
        questionNotification.setCreatedAt(LocalDateTime.now()); // dodano
        return questionNotificationRepository.save(questionNotification); // dodano
        //throw new NotFoundException("Question with id: " + questionNotification.getQuestion().getId() + " doesn't exist");
    }

    //izlistaj sve question notifikacije
    public List<QuestionNotification> getAllQuestionNotifications() {
        List<QuestionNotification> questionNotifications = new ArrayList<>();
        questionNotifications.addAll(questionNotificationRepository.getAllQuestionNotifications());
        if (questionNotifications.isEmpty())
            throw new NotFoundException("There is no question notifications");
        return questionNotifications;
    }

    //daj question notifikaciju po id
    public QuestionNotification getQuestionNotificationById(Long questionNotificationId) {
        Optional<QuestionNotification> questionNotification = questionNotificationRepository.findQuestionNotificationById(questionNotificationId);
        if (!questionNotification.isPresent())
            throw new NotFoundException("Question notification with id: " + questionNotificationId + " doesn't exist.");
        return (QuestionNotification) questionNotification.get();
    }

    //izlistat sve notifikacije u kojim se spominje neki user
    public List<QuestionNotification> getAllClientQuestionNotifications(Long clientId) {
        List<QuestionNotification> questionNotifications = new ArrayList<>();
        Optional<User> user = userRepository.findById(clientId);
        if (user.isPresent()) {
            //if (doesContain(user.get(), ROLE_CLIENT)) {
            if (userRepository.doesExistRoleName(clientId) != null) {
                List<Notification> notifications = notificationRepository.findAllByUserId(clientId);
                for (Notification notification : notifications) {
                    if (notification instanceof QuestionNotification) {
                        questionNotifications.add((QuestionNotification) notification);
                    }
                }
                if (questionNotifications.isEmpty())
                    throw new NotFoundException("User with id: " + clientId + " occurs in no question notifications.");
            } else throw new ApiRequestException("User with id: " + clientId + " isn't client.");
        } else throw new NotFoundException("User with id: " + clientId + " doesn't exist.");
        return questionNotifications;
    }

    //daj notifikaciju po id pitanja
    public QuestionNotification getQuestionNotificationByQuestion(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        List<QuestionNotification> notifications = new ArrayList<>();
        if (question.isPresent()) {
            notifications = questionRepository.findAllByQuestionId(questionId);
            if (notifications.size() > 1)
                throw new ApiRequestException("Question with id: " + questionId + " exists in more than one notification.");
            if (notifications.isEmpty())
                throw new NotFoundException("Question notification with question id: " + questionId + " doesn't exist.");

        } else throw new NotFoundException("Question with id: " + questionId + " doesn't exist.");
        return notifications.get(0);
    }

    //daj sve notifikacije napravljene izmedju dva datuma
    public List<QuestionNotification> getAllQuestionNotificationsBetweenTwoDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        List<QuestionNotification> questionNotifications = new ArrayList<>();
        List<Notification> notifications=new ArrayList<>();
        try {
            notifications = notificationService.getAllNotificationsBetweenTwoDates(localDateTime1, localDateTime2);
        }
        catch (RuntimeException e){
            throw e;
        }
        for (Notification notification : notifications) {
            if (notification instanceof QuestionNotification)
                questionNotifications.add((QuestionNotification) notification);
        }
        if (questionNotifications.isEmpty())
            throw new NotFoundException("There is no question notifications between two dates.");
        return questionNotifications;
    }


}