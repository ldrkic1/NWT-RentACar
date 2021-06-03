package ba.unsa.etf.notificationmicroservice.controllers;
import ba.unsa.etf.notificationmicroservice.models.*;
import ba.unsa.etf.notificationmicroservice.services.QuestionService;
import ba.unsa.etf.notificationmicroservice.services.ReservationNotificationService;
import ba.unsa.etf.notificationmicroservice.services.ReservationService;
import ba.unsa.etf.notificationmicroservice.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReservationNotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @Test
    public void allReservationNotifications() throws Exception {
        mockMvc.perform(get("/reservationNotifications/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getReservationNotificationById () throws Exception {
        mockMvc.perform( get("/reservationNotifications/reservationNotification?id=3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));

        mockMvc.perform( get("/reservationNotifications/reservationNotification?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Reservation notification with id: 900 doesn't exist."));

        mockMvc.perform( get("/reservationNotifications/reservationNotification?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Reservation notification with id: 1 doesn't exist."));
    }

    @Test
    public void allClientReservationNotifications() throws Exception {
        mockMvc.perform(get("/reservationNotifications/client?id=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/reservationNotifications/client?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 1 occurs in no reservation notifications."));

        mockMvc.perform(get("/reservationNotifications/client?id=3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with id: 3 isn't client."));

        mockMvc.perform(get("/reservationNotifications/client?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 900 doesn't exist."));
    }

    @Test
    public void reservationNotificationsByReservation() throws Exception {
        mockMvc.perform(get("/reservationNotifications/reservation?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/reservationNotifications/reservation?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Reservation with id: 900 doesn't exist."));
    }

    @Test
    public void getAllReservationNotificationsBetweenTwoDatesC() throws Exception {
        mockMvc.perform(get("/reservationNotifications/between?localDateTime1=2050-04-06T23:59:30&localDateTime2=2015-04-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First date from the future."));

        mockMvc.perform(get("/reservationNotifications/between?localDateTime1=2020-04-06T23:59:30&localDateTime2=2050-04-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Second date from the future."));

        mockMvc.perform(get("/reservationNotifications/between?localDateTime1=2021-03-06T23:59:30&localDateTime2=2021-01-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First date must be after second date."));

        mockMvc.perform(get("/reservationNotifications/between?localDateTime1=2010-04-06T23:59:30&localDateTime2=2011-01-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("There is no notifications between two dates."));

        mockMvc.perform(get("/reservationNotifications/between?localDateTime1=2021-01-29T20:30:40&localDateTime2=2021-03-31T18:01:00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void addReservationNotification() throws Exception {
        ReservationNotification rn1 = new ReservationNotification();
        Reservation reservation1 = new Reservation();
        reservation1.setReservationNotification(null);
        reservationService.save(reservation1);
        reservation1.setReservationNotification(rn1);

        User user=userService.getUser("neko").get();
        rn1.setUser(user);
        rn1.setReservation(reservation1);
        rn1.setTitle("Notifikacija za rezervaciju");
        rn1.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je izvrsio rezervaciju ciji je id: "+reservation1.getId());
        String json = new ObjectMapper().writeValueAsString(rn1);

        mockMvc.perform(post("/reservationNotifications/newReservationNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        ReservationNotification rn2 = new ReservationNotification();
        List<ReservationNotification> reservation=reservationService.getReservation(1L);
        //question.setQuestionNotification(null);

        rn2.setUser(user);
        rn2.setReservation(reservation.get(0).getReservation());
        rn2.setTitle("Notifikacija za rezervaciju");
        rn2.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je izvrsio rezervaciju: "+reservation.get(0).getTitle());
        json = new ObjectMapper().writeValueAsString(rn2);
        //json = json.replace("\"ROLE_CLIENT\"","{\"roleName\": \"ROLE_CLIENT\"}");
        System.out.println("+++++++++++"+ json);

        mockMvc.perform(post("/reservationNotifications/newReservationNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Reservation notification with reservation id: 1 already exists"));

    }
}
*/

