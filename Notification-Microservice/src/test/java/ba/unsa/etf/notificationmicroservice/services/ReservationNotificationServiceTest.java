package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationNotificationServiceTest {
    @Autowired
    ReservationNotificationService reservationNotificationService;

    @Autowired
    UserService userService;

    @Autowired
    ReservationService reservationService;


    @Test
    public void getAllReservationNotificationsTest() throws Exception {
        assertThat(reservationNotificationService.getAllReservationNotifications().size()).isNotEqualTo(0);
    }
    @Test
    public void getReservationNotificationByIdTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> reservationNotificationService.getReservationNotificationById(990L));
        assertTrue(exception.getMessage().contains("Reservation notification with id: 990 doesn't exist."));

        assertThat(reservationNotificationService.getReservationNotificationById(3L).getId()).isEqualTo(3L);
    }
    @Test
    public void getAllReservationNotificationsByClientTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> reservationNotificationService.getAllClientReservationNotifications(990L));
        assertTrue(exception.getMessage().contains("User with id: 990 doesn't exist."));

        exception = assertThrows(ApiRequestException.class, () -> reservationNotificationService.getAllClientReservationNotifications(3L));
        assertTrue(exception.getMessage().contains("User with id: 3 isn't client."));

        exception = assertThrows(NotFoundException.class, () -> reservationNotificationService.getAllClientReservationNotifications(1L));
        assertTrue(exception.getMessage().contains("User with id: 1 occurs in no reservation notifications."));

        assertThat(reservationNotificationService.getAllClientReservationNotifications(2L).size()).isNotEqualTo(0);
    }
    @Test
    public void getReservationNotificationByReservationTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> reservationNotificationService.getReservationNotificationByReservation(990L));
        assertTrue(exception.getMessage().contains("Reservation with id: 990 doesn't exist."));

        assertThat(reservationNotificationService.getReservationNotificationByReservation(1L).getId()).isEqualTo(3L);
    }
    @Test
    public void getAllReservationNotificationsBetweenTwoDatesTest() throws Exception {
        Exception exception = assertThrows(ValidationException.class, () -> reservationNotificationService.getAllReservationNotificationsBetweenTwoDates(LocalDateTime.of(2022, Month.JANUARY, 29, 20, 30, 40), LocalDateTime.now()));
        assertTrue(exception.getMessage().contains("First date from the future."));

        exception = assertThrows(ValidationException.class, () -> reservationNotificationService.getAllReservationNotificationsBetweenTwoDates(LocalDateTime.now(), LocalDateTime.of(2022, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("Second date from the future."));

        exception = assertThrows(ValidationException.class, () -> reservationNotificationService.getAllReservationNotificationsBetweenTwoDates(LocalDateTime.of(2021, Month.MARCH, 29, 20, 30, 40),LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("First date must be after second date."));

        exception = assertThrows(NotFoundException.class, () -> reservationNotificationService.getAllReservationNotificationsBetweenTwoDates(LocalDateTime.of(2010, Month.MARCH, 29, 20, 30, 40),LocalDateTime.of(2011, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("There is no notifications between two dates."));

        assertThat(reservationNotificationService.getAllReservationNotificationsBetweenTwoDates(LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40),LocalDateTime.now()).size()).isNotEqualTo(0);
    }

    @Test
    public void addReservationNotificationTest()throws Exception{
        ReservationNotification rn1 = new ReservationNotification();
        Reservation reservation = new Reservation();
        reservation.setId(50000L);
        reservation.setReservationNotification(rn1);
        rn1.setReservation(reservation);
        User user=userService.getUser("idedic2").get();
        rn1.setUser(user);
        rn1.setReservation(reservation);
        rn1.setTitle("Notifikacija za rezervaciju");
        rn1.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je izvrsio rezervaciju: "+reservation.getId());

        Long sizeBefore= (long) reservationNotificationService.getAllReservationNotifications().size();
        reservationNotificationService.addReservationNotification(rn1);
        Long sizeAfter= (long) reservationNotificationService.getAllReservationNotifications().size();

        assertThat(sizeBefore).isLessThan(sizeAfter);

        ReservationNotification rn2 = new ReservationNotification();
        List<ReservationNotification> reservationNotifications=reservationService.getReservation(1L);
        rn2.setReservation(reservationNotifications.get(0).getReservation());
        rn2.setUser(user);
        rn2.setTitle("Notifikacija za rezervaciju");
        rn2.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je izvrsio rezervaciju: "+reservation.getId());

        Exception exception = assertThrows(ApiRequestException.class, () -> reservationNotificationService.addReservationNotification(rn2));
        assertTrue(exception.getMessage().contains("Reservation notification with reservation id: 1 already exists"));

    }
}
