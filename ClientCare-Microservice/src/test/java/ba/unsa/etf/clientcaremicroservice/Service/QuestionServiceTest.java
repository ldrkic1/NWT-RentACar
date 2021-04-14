package ba.unsa.etf.clientcaremicroservice.Service;

import static org.junit.jupiter.api.Assertions.*;

import ba.unsa.etf.clientcaremicroservice.Exception.ApiRequestException;
import ba.unsa.etf.clientcaremicroservice.Exception.NotFoundException;
import ba.unsa.etf.clientcaremicroservice.Exception.ValidationException;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getAllQuestionsTest() {
        assertEquals(4, questionService.getQuestions(null).size());
        assertTrue(questionService.getQuestions("Duzina najma").size() == 2);
    }

    @Test
    public void getAnsweredQuestionsTest() {
        assertEquals(2, questionService.getAllAnsweredQuestions().size());
    }

    @Test
    public void getUnansweredQuestionsTest() {
        assertEquals(2, questionService.getAllUnansweredQuestions().size());
    }

    @Test
    public void getQuestionByIdTest() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> questionService.getQuestionById(9L));
        assertTrue(exception.getMessage().contains("Question with id: 9 doesn't exist."));
        Question  qusetion = questionService.getQuestionById(1L);
        assertAll(
                () -> assertTrue(qusetion.isAnswered()),
                () -> assertEquals("Osiguranje automobila", qusetion.getTitle()),
                () -> assertTrue(qusetion.getUser().getFirstName().equals("Mujo"))
        );
    }

    @AfterAll
    public void deleteQuestionTest() {
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> questionService.deleteQuestionById(10L)),
                () -> assertDoesNotThrow(() -> questionService.deleteQuestionById(4L)),
                () -> assertEquals(3, questionService.getQuestions(null).size())
        );
    }

    @Test
    public void addQuestionTest() {
        Question question = new Question();
        User user = new User();
        user.setFirstName("Lala");
        user.setLastName("Lalic");
        user.setUsername("llalic");
        question.setUser(user);
        Exception e = assertThrows(
                NotFoundException.class,
                () -> questionService.addQuestion(question));
        assertTrue(e.getMessage().contains("There is no client with username: llalic"));
        user = userService.getUserById(4L).get();
        question.setUser(user);
        e = assertThrows(
                ValidationException.class,
                () -> questionService.addQuestion(question));
        assertTrue(e.getMessage().contains("Title is required."));

        question.setTitle("Naslov");
        e = assertThrows(
                ValidationException.class,
                () -> questionService.addQuestion(question));
        assertTrue(e.getMessage().contains("Question is required."));

        question.setQuestion("Novo pitanje?");
        assertDoesNotThrow(() -> questionService.addQuestion(question));
        questionService.deleteQuestionById(6L);
    }

    @Test
    public void addQuestionTest2() {
        int numOfUsers = userRepository.findAll().size();
        Question question = new Question();
        User user = new User();
        user.setUsername("irma");
        question.setUser(user);
        question.setUser(user);
        question.setTitle("Naslov");
        question.setQuestion("Novo pitanje?");
        assertDoesNotThrow(() -> questionService.addQuestion(question));
        questionService.deleteQuestionById(5L);
        assertEquals(numOfUsers+1, userRepository.findAll().size());
    }



}