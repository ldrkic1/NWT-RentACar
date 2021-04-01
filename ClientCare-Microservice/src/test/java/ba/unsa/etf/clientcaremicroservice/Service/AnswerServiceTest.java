package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.ApiRequestException;
import ba.unsa.etf.clientcaremicroservice.Exception.NotFoundException;
import ba.unsa.etf.clientcaremicroservice.Exception.ValidationException;
import ba.unsa.etf.clientcaremicroservice.Model.Answer;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Model.Question;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceTest {

    @Autowired
    AnswerService answerService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;
    @Test
    public void getAllAnswersTest() {
        assertTrue(answerService.getAnswers().size() == 2);
    }

    @Test
    public void getAnswerOnQuestion() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> answerService.getAnswerOnQuestion(7L)
        );
        assertTrue(exception.getMessage().contains("Question with id: 7 doesn't exist."));

        exception = assertThrows(
                ApiRequestException.class,
                () -> answerService.getAnswerOnQuestion(3L)
        );
        assertTrue(exception.getMessage().contains("Question with id: 3 isn't answered!"));

        Answer answer = answerService.getAnswerOnQuestion(1L);
        assertTrue(answer.getQuestion().getTitle().equals("Osiguranje automobila"));

    }

    @Test
    public void addAnswerOnQuestionTest() {
        User user = userService.getUserById(2L).get();
        Answer answer = new Answer();
        answer.setUser(user);
        Question question = questionService.getQuestionById(5L);
        answer.setQuestion(question);
        Exception e = assertThrows(
                ApiRequestException.class,
                () -> answerService.addAnswerOnQuestion(answer, question.getId()));
        assertTrue(e.getMessage().contains("User Lamija Drkić isn't admin!"));
        user = new User("Nepostojeci","User");
        answer.setUser(user);
        e = assertThrows(
                NotFoundException.class,
                () -> answerService.addAnswerOnQuestion(answer, question.getId()));
        assertTrue(e.getMessage().contains("User Nepostojeci User doesn't exist"));
        user = userService.getUserById(1L).get();
        answer.setUser(user);
        e = assertThrows(
                NotFoundException.class,
                () -> answerService.addAnswerOnQuestion(answer, 15L));
        assertTrue(e.getMessage().contains("Question with id: 15 doesn't exist."));
        e = assertThrows(
                ApiRequestException.class,
                () -> answerService.addAnswerOnQuestion(answer, 1L));
        assertTrue(e.getMessage().contains("Question with id: 1 is answered. Answer id: 1."));
        e = assertThrows(
                ValidationException.class,
                () -> answerService.addAnswerOnQuestion(answer, question.getId()));
        assertTrue(e.getMessage().contains("Answer is required."));
        answer.setAnswer("Odgovor");
        assertDoesNotThrow(() -> answerService.addAnswerOnQuestion(answer, question.getId()));
    }

    @Test
    public void deleteAnswerTest() {
        Exception e = assertThrows(
                NotFoundException.class,
                () -> answerService.deleteAnswerById(16L));
        assertTrue(e.getMessage().contains("Answer with id: 16 doesn't exist."));
        assertDoesNotThrow(() -> answerService.deleteAnswerById(5L));
    }
}