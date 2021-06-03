package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.exceptions.ApiRequestException;
import ba.unsa.etf.notificationmicroservice.exceptions.NotFoundException;
import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionNotificationServiceTest {
    @Autowired
    QuestionNotificationService questionNotificationService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @Test
    public void getAllQuestionNotificationsTest() throws Exception {
        assertThat(questionNotificationService.getAllQuestionNotifications().size()).isNotEqualTo(0);
    }
    @Test
    public void getQuestionNotificationByIdTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> questionNotificationService.getQuestionNotificationById(990L));
        assertTrue(exception.getMessage().contains("Question notification with id: 990 doesn't exist."));

        assertThat(questionNotificationService.getQuestionNotificationById(1L).getId()).isEqualTo(1L);
    }
    @Test
    public void getAllQuestionNotificationsByClientTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> questionNotificationService.getAllClientQuestionNotifications(990L));
        assertTrue(exception.getMessage().contains("User with id: 990 doesn't exist."));

        assertThat(questionNotificationService.getAllClientQuestionNotifications(4L).size()).isNotEqualTo(0);
    }
    @Test
    public void getQuestionNotificationByQuestionTest() throws Exception {
        Exception exception = assertThrows(NotFoundException.class, () -> questionNotificationService.getQuestionNotificationByQuestion(990L));
        assertTrue(exception.getMessage().contains("Question with id: 990 doesn't exist."));

        assertThat(questionNotificationService.getQuestionNotificationByQuestion(1L).getId()).isEqualTo(1L);
    }
    @Test
    public void getAllQuestionNotificationsBetweenTwoDatesTest() throws Exception {
        Exception exception = assertThrows(ValidationException.class, () -> questionNotificationService.getAllQuestionNotificationsBetweenTwoDates(LocalDateTime.of(2022, Month.JANUARY, 29, 20, 30, 40), LocalDateTime.now()));
        assertTrue(exception.getMessage().contains("First date from the future."));

        exception = assertThrows(ValidationException.class, () -> questionNotificationService.getAllQuestionNotificationsBetweenTwoDates(LocalDateTime.now(), LocalDateTime.of(2022, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("Second date from the future."));

        exception = assertThrows(ValidationException.class, () -> questionNotificationService.getAllQuestionNotificationsBetweenTwoDates(LocalDateTime.of(2021, Month.MARCH, 29, 20, 30, 40),LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("First date must be after second date."));

        exception = assertThrows(NotFoundException.class, () -> questionNotificationService.getAllQuestionNotificationsBetweenTwoDates(LocalDateTime.of(2010, Month.MARCH, 29, 20, 30, 40),LocalDateTime.of(2011, Month.JANUARY, 29, 20, 30, 40)));
        assertTrue(exception.getMessage().contains("There is no notifications between two dates."));

        assertThat(questionNotificationService.getAllQuestionNotificationsBetweenTwoDates(LocalDateTime.of(2021, Month.JANUARY, 29, 20, 30, 40),LocalDateTime.now()).size()).isNotEqualTo(0);
    }

    /*
    @Test
    public void addQuestionNotificationTest()throws Exception{
        QuestionNotification qn1 = new QuestionNotification();
        Question question1 = new Question();
        question1.setId(50000L);
        question1.setTitle("Pitanje");
        question1.setQuestionNotification(qn1);
        qn1.setQuestion(question1);
        User user=userService.getUser("idedic2").get();
        qn1.setUser(user);
        qn1.setQuestion(question1);
        qn1.setTitle("Notifikacija za pitanje");
        qn1.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je postavio pitanje: "+question1.getTitle());

        Long sizeBefore= (long) questionNotificationService.getAllQuestionNotifications().size();
        questionNotificationService.addQuestionNotification(qn1);
        Long sizeAfter= (long) questionNotificationService.getAllQuestionNotifications().size();

        assertThat(sizeBefore).isLessThan(sizeAfter);

        QuestionNotification qn2 = new QuestionNotification();
        List<QuestionNotification> question=questionService.getQuestion(1L);
        qn2.setQuestion(question.get(0).getQuestion());
        qn2.setUser(user);
        qn2.setTitle("Notifikacija za pitanje");
        qn2.setContent("Klijent "+user.getFirstName()+" "+user.getLastName()+" je postavio pitanje: "+question1.getTitle());

        Exception exception = assertThrows(ApiRequestException.class, () -> questionNotificationService.addQuestionNotification(qn2));
        assertTrue(exception.getMessage().contains("Question notification with question id: 1 already exists"));


    }
*/
}
