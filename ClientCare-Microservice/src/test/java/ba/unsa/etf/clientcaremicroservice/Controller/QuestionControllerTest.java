package ba.unsa.etf.clientcaremicroservice.Controller;
import ba.unsa.etf.clientcaremicroservice.Model.*;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import ba.unsa.etf.clientcaremicroservice.Service.ReviewService;
import ba.unsa.etf.clientcaremicroservice.Service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashSet;
import java.util.Set;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    public void allQuestions() throws Exception {
        mockMvc.perform(get("/question/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void allAnsweredQuestions() throws Exception {
        mockMvc.perform(get("/question/answered")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void allUnansweredQuestions() throws Exception {
        mockMvc.perform(get("/question/unanswered")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getQuestionById () throws Exception {
        mockMvc.perform( get("/question?id=3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    public void getQuestionById2 () throws Exception {
        mockMvc.perform( get("/question?id=8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Question with id: 8 doesn't exist."));
    }

    @Test
    public void allClientQuestions() throws Exception {
        mockMvc.perform(get("/question/client?clientID=6")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void allClienQuestion2() throws Exception {
        mockMvc.perform(get("/question/client?clientID=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with id: 1 isn't client."));
    }

    @Test
    public void deleteQuestion() throws Exception {
        mockMvc.perform(delete("/question?id=2")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    public void delete2() throws Exception {
        mockMvc.perform(delete("/question?id=9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Question with id: 9 doesn't exist."));

    }

    @Test
    public void addQuestion() throws Exception {
        String json = "{ \"title\": \"Naslov\",\"question\": \"Tekst pitanja\", \"answered\": \"false\", \"user\": { \"username\": \"ldrkic1\" } }";

        mockMvc.perform(post("/question/newQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        String json1 = "{ \"title\": \"\",\"question\": \"Tekst pitanja\", \"answered\": \"false\", \"user\": { \"username\": \"ldrkic1\" } }";
        mockMvc.perform(post("/question/newQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Title is required."));

        String json2 = "{ \"title\": \"naslov\",\"question\": \"   \", \"answered\": \"false\", \"user\": { \"username\": \"ldrkic1\" } }";
        mockMvc.perform(post("/question/newQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Question is required."));

        String json3 = "{ \"title\": \"naslov\",\"question\": \"Tekst pitanja\", \"answered\": \"false\", \"user\": { \"username\": \"ldrkic\" } }";
        mockMvc.perform(post("/question/newQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("There is no client with username: ldrkic"));

    }
}