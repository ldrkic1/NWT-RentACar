package ba.unsa.etf.notificationmicroservice.controllers;
import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.models.Role;
import ba.unsa.etf.notificationmicroservice.models.User;
import ba.unsa.etf.notificationmicroservice.services.QuestionService;
import ba.unsa.etf.notificationmicroservice.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QuestionNotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @Test
    public void getAllQuestionNotificationsC() throws Exception {
        mockMvc.perform(get("/questionNotifications/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getQuestionNotificationByIdC () throws Exception {
        mockMvc.perform( get("/questionNotifications/questionNotification?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform( get("/questionNotifications/questionNotification?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Question notification with id: 900 doesn't exist."));

    }

    @Test
    public void getAllClientQuestionNotificationsC() throws Exception {
        mockMvc.perform(get("/questionNotifications/client?id=5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/questionNotifications/client?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 900 doesn't exist."));
    }

    @Test
    public void getQuestionNotificationByQuestionC() throws Exception {
        mockMvc.perform(get("/questionNotifications/question?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/questionNotifications/question?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Question with id: 900 doesn't exist."));
    }

    @Test
    public void getAllQuestionNotificationsBetweenTwoDatesC() throws Exception {
        mockMvc.perform(get("/questionNotifications/between?localDateTime1=2050-04-06T23:59:30&localDateTime2=2015-04-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First date from the future."));

        mockMvc.perform(get("/questionNotifications/between?localDateTime1=2020-04-06T23:59:30&localDateTime2=2050-04-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Second date from the future."));

        mockMvc.perform(get("/questionNotifications/between?localDateTime1=2021-03-06T23:59:30&localDateTime2=2021-01-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First date must be after second date."));

        mockMvc.perform(get("/questionNotifications/between?localDateTime1=2010-04-06T23:59:30&localDateTime2=2011-01-06T23:59:30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("There is no notifications between two dates."));

    }
/*
    @Test
    public void addQuestionNotification() throws Exception {
        QuestionNotification qn1 = new QuestionNotification();
        Question question1 = new Question();
        question1.setId(5L);
        question1.setTitle("Pitanje");
        question1.setQuestionNotification(null);
        questionService.save(question1);
        question1.setQuestionNotification(qn1);
        User user=userService.getUser("mmujic2").get();
        qn1.setUser(user);
        qn1.setQuestion(question1);
        qn1.setTitle("Notifikacija za pitanje");
        qn1.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je postavio pitanje: "+question1.getTitle());
        String json = new ObjectMapper().writeValueAsString(qn1);
        //json = json.replace("\"ROLE_CLIENT\"","{\"roleName\": \"ROLE_CLIENT\"}");
        System.out.println("+++++++++++"+ json);

        mockMvc.perform(post("/questionNotifications/newQuestionNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        QuestionNotification qn2 = new QuestionNotification();
        List<QuestionNotification> question=questionService.getQuestion(1L);
        //question.setQuestionNotification(null);

        qn2.setUser(user);
        qn2.setQuestion(question.get(0).getQuestion());
        System.out.println("id: "+question.get(0).getQuestion().getId());
        qn2.setTitle("Notifikacija za pitanje");
        qn2.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je postavio pitanje: "+question.get(0).getTitle());
        json = new ObjectMapper().writeValueAsString(qn2);
        //json = json.replace("\"ROLE_CLIENT\"","{\"roleName\": \"ROLE_CLIENT\"}");
        System.out.println("+++++++++++"+ json);

        mockMvc.perform(post("/questionNotifications/newQuestionNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Question notification with question id: 1 already exists"));

    }
*/
}
