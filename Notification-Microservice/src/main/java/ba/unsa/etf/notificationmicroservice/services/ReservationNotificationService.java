package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.models.*;
import ba.unsa.etf.notificationmicroservice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ba.unsa.etf.notificationmicroservice.RoleName.ROLE_CLIENT;

@Service
public class ReservationNotificationService {
    @Autowired
    private ReservationNotificationRepository reservationNotificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private NotificationService notificationService;
    private Boolean doesContain(User user, RoleName roleName) {
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals(roleName))
                return true;
        }
        return false;
    }
    //dodaj reservation notifikaciju
    public ReservationNotification addReservationNotification(ReservationNotification reservationNotification) {
        Optional<User> user = userRepository.findByUsername(reservationNotification.getUser().getUsername());
        if (user.isPresent()) {
            System.out.println("velicinaaa " + user.get().getRoles().size());
            //if (doesContain(user.get(), ROLE_CLIENT)) {
            if(userRepository.doesExistRoleName(user.get().getId())!=null){
                reservationNotification.setUser(user.get());
            } else
                throw new ApiRequestException(reservationNotification.getUser().getFirstName() + " " + reservationNotification.getUser().getLastName() + " isn't client.");
        } else
            throw new NotFoundException("Client with username: " + reservationNotification.getUser().getUsername() + " doesn't exist.");


        //questionNotification.setUser(user.get());
        Optional<Reservation> reservation = reservationRepository.findById(reservationNotification.getReservation().getId());
        if (!reservation.isPresent()) {
            reservation = Optional.of(new Reservation()); // dodano
        }
        else{
            if(!reservationRepository.findAllByReservationId(reservation.get().getId()).isEmpty())
                throw new ApiRequestException("Reservation notification with reservation id: "+reservation.get().getId()+" already exists");
        }

            reservation.get().setReservationNotification(reservationNotification);
            reservationNotification.setReservation(reservation.get());
            reservationNotification.setCreatedAt(LocalDateTime.now());
            return reservationNotificationRepository.save(reservationNotification);

    }

    //izlistaj sve question notifikacije
    public List<ReservationNotification> getAllReservationNotifications() {
        List<ReservationNotification>reservationNotifications=new ArrayList<>();
        reservationNotifications.addAll(reservationNotificationRepository.getAllReservationNotifications());
        if(reservationNotifications.isEmpty())
            throw new NotFoundException("There is no reservation notifications");
        return reservationNotifications;
    }
    //daj reservation notifikaciju po id
    public ReservationNotification getReservationNotificationById(Long reservationNotificationId) {
        Optional<ReservationNotification> reservationNotification=reservationNotificationRepository.findReservationNotificationById(reservationNotificationId);
        if(!reservationNotification.isPresent())
            throw new NotFoundException("Reservation notification with id: " + reservationNotificationId + " doesn't exist.");
        return (ReservationNotification) reservationNotification.get();
    }

    //izlistat sve notifikacije u kojim se spominje neki user
    public List<ReservationNotification> getAllClientReservationNotifications(Long clientId){
        List<ReservationNotification>reservationNotifications=new ArrayList<>();
        Optional<User> user = userRepository.findById(clientId);
        if(user.isPresent()) {
            //if (doesContain(user.get(), ROLE_CLIENT)) {
            if(userRepository.doesExistRoleName(clientId)!=null){
                List<Notification> notifications= notificationRepository.findAllByUserId(clientId);
                for(Notification notification: notifications){
                    if(notification instanceof ReservationNotification)
                        reservationNotifications.add((ReservationNotification) notification);
                }
                if(reservationNotifications.isEmpty())
                    throw new NotFoundException("User with id: "+clientId+" occurs in no reservation notifications.");
            }
            else throw new ApiRequestException("User with id: " + clientId + " isn't client.");
        }
        else throw new NotFoundException("User with id: " + clientId + " doesn't exist.");
        return reservationNotifications;
    }
    //daj notifikaciju po id rezervacije
    public ReservationNotification getReservationNotificationByReservation(Long reservationId){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        List<ReservationNotification> notifications=new ArrayList<>();
        if(reservation.isPresent()) {
            notifications= reservationRepository.findAllByReservationId(reservationId);
            if(notifications.size()>1)
                throw new ApiRequestException("Reservation with id: "+ reservationId+" exists in more than one notification");
            if(notifications.isEmpty())
                throw new NotFoundException("Reservation notification with reservation id: "+reservationId+" doesn't exist.");

        }
        else throw new NotFoundException("Reservation with id: " + reservationId + " doesn't exist.");
        return notifications.get(0);
    }

    //daj sve notifikacije napravljene izmedju dva datuma
    public List<ReservationNotification> getAllReservationNotificationsBetweenTwoDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        List<ReservationNotification> questionNotifications = new ArrayList<>();
        List<Notification> notifications=new ArrayList<>();
        try {
            notifications = notificationService.getAllNotificationsBetweenTwoDates(localDateTime1, localDateTime2);
        }
        catch (RuntimeException e){
            throw e;
        }
        for (Notification notification : notifications) {
            if (notification instanceof ReservationNotification)
                questionNotifications.add((ReservationNotification) notification);
        }
        if (questionNotifications.isEmpty())
            throw new NotFoundException("There is no reservation notifications between two dates.");
        return questionNotifications;
    }

}
