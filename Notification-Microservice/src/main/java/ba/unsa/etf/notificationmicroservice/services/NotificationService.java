package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.Role;
import ba.unsa.etf.notificationmicroservice.models.User;
import ba.unsa.etf.notificationmicroservice.repositories.NotificationRepository;
import ba.unsa.etf.notificationmicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ba.unsa.etf.notificationmicroservice.RoleName.ROLE_CLIENT;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    //izlistaj sve notifikacije
    public List<Notification> getAllNotifications(){
        if(notificationRepository.getAllNotifications().isEmpty())
            throw new NotFoundException("There is no notifications");
        return notificationRepository.findAll();
    }

    //izlistaj sve notifikacije po userID
    public List<Notification> getAllNotificationsByClient(Long clientId){
        Optional<User> user = userRepository.findById(clientId);
        if(user.isPresent()) {
            if(userRepository.doesExistRoleName(clientId)!=null)
                return notificationRepository.findAllByUserId(clientId);
             throw new ApiRequestException("User with id: " + clientId + " isn't client.");
        }
        throw new NotFoundException("User with id: " + clientId + " doesn't exist.");
    }

    //izlistaj sve notifikacije po username
    /*public List<Notification> getAllNotificationsByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            if(user.get().getRoles().contains(ROLE_CLIENT)) return notificationRepository.findAllByUsername(username);
            else throw new ApiRequestException("User with username: " + username + " isn't client.");
        }
        else throw new NotFoundException("Client with username: " + username + " doesn't exist.");
    }*/
    //nadji notifikaciju po id
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new NotFoundException("Notification with id: " + notificationId + " doesn't exist."));
    }

    //daj sve notifikacije napravljene izmedju dva datuma
    public List<Notification> getAllNotificationsBetweenTwoDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2){
       System.out.println("u notifikacijamaaa");
        if(localDateTime1.isAfter(LocalDateTime.now()))
            throw new ValidationException("First date from the future.");
        if(localDateTime2.isAfter(LocalDateTime.now()))
            throw new ValidationException("Second date from the future.");
        if(localDateTime1.isAfter(localDateTime2))
            throw new ValidationException("First date must be after second date.");
        List<Notification>notifications=new ArrayList<>();
        notifications=notificationRepository.getAllBetwennTwoDates(localDateTime1, localDateTime2);
        if(notifications.isEmpty())
            throw new NotFoundException("There is no notifications between two dates.");
        return notifications;
    }

}
