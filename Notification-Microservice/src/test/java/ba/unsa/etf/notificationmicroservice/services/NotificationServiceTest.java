package ba.unsa.etf.notificationmicroservice.services;
import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@RunWith(SpringRunner.class)
public class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;

    @Test
    public void getAllNotificationsTest() throws Exception {
            assertThat(notificationService.getAllNotifications().size()).isNotEqualTo(0);
        }
    @Test
    public void getAllNotificationsByClientTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> notificationService.getAllNotificationsByClient(990L));
        assertTrue(exception.getMessage().contains("User with id: 990 doesn't exist."));

        assertThat(notificationService.getAllNotificationsByClient(4L).size()).isNotEqualTo(0);
    }

    @Test
    public void getNotificationByIdTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> notificationService.getNotificationById(990L));
        assertTrue(exception.getMessage().contains("Notification with id: 990 doesn't exist."));

        assertThat(notificationService.getNotificationById(1L).getId()).isEqualTo(1L);
    }

    @Test
    public void getAllNotificationsBetweenTwoDatesTest() throws Exception {
        Exception exception = assertThrows(ValidationException.class, () -> notificationService.getAllNotificationsBetweenTwoDates(LocalDateTime.of(2050, Month.JANUARY, 29, 20, 30, 40), LocalDateTime.now()));
        assertTrue(exception.getMessage().contains("First date from the future."));

        exception = assertThrows(ValidationException.class, () -> notificationService.getAllNotificationsBetweenTwoDates(LocalDateTime.now(), LocalDateTime.of(2050, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("Second date from the future."));

        exception = assertThrows(ValidationException.class, () -> notificationService.getAllNotificationsBetweenTwoDates(LocalDateTime.of(2021, Month.MARCH, 29, 20, 30, 40),LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("First date must be after second date."));

        exception = assertThrows(NotFoundException.class, () -> notificationService.getAllNotificationsBetweenTwoDates(LocalDateTime.of(2010, Month.MARCH, 29, 20, 30, 40),LocalDateTime.of(2011, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("There is no notifications between two dates."));

        assertThat(notificationService.getAllNotificationsBetweenTwoDates(LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40),LocalDateTime.now()).size()).isNotEqualTo(0);
    }

}
