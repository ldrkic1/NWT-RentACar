package ba.unsa.etf.notificationmicroservice.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllNotificationsTestC() throws Exception {
        mockMvc.perform(get("/notifications/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getNotificationByIdTestC () throws Exception {
        mockMvc.perform( get("/notifications/notification?id=3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));

        mockMvc.perform( get("/notifications/notification?id=990")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Notification with id: 990 doesn't exist."));
    }

    @Test
    public void getAllNotificationsByClientTestC() throws Exception {
        mockMvc.perform(get("/notifications/client?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/notifications/client?id=3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with id: 3 isn't client."));

        mockMvc.perform(get("/notifications/client?id=990")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 990 doesn't exist."));
    }

    @Test
    public void getAllNotificationsBetweenTwoDatesTestC() throws Exception {
        mockMvc.perform(get("/notifications/between?localDateTime1=2050-04-06T23:59:30&localDateTime2=2015-04-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First date from the future."));

        mockMvc.perform(get("/notifications/between?localDateTime1=2020-04-06T23:59:30&localDateTime2=2050-04-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Second date from the future."));

        mockMvc.perform(get("/notifications/between?localDateTime1=2021-03-06T23:59:30&localDateTime2=2021-01-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First date must be after second date."));

        mockMvc.perform(get("/notifications/between?localDateTime1=2010-04-06T23:59:30&localDateTime2=2011-01-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("There is no notifications between two dates."));

        mockMvc.perform(get("/notifications/between?localDateTime1=2021-01-29T20:30:40&localDateTime2=2021-03-31T18:01:00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
