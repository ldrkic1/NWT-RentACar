package ba.unsa.etf.clientcaremicroservice.Controller;
import ba.unsa.etf.clientcaremicroservice.Model.*;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import ba.unsa.etf.clientcaremicroservice.Service.QuestionService;
import ba.unsa.etf.clientcaremicroservice.Service.ReviewService;
import ba.unsa.etf.clientcaremicroservice.Service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
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
public class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    QuestionService questionService;

    @Test
    public void allAnswers() throws Exception {
        mockMvc.perform(get("/answer/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAnswerOnQuestion() throws Exception {
        mockMvc.perform(get("/answer?questionID=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("Prema zakonu, svi automobili, svih agencija su kasko osigurani."));
    }

    @Test
    public void answerQuestion() throws Exception{
        String json = "{ \"answer\": \"odgvovor na pitanje\", \"user\": { \"username\": \"aadmin21\" } }";
        mockMvc.perform(post("/answer?questionID=4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        mockMvc.perform(post("/answer?questionID=9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/answer?questionID=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        String json1 = "{ \"answer\": \"odgvovor na pitanje\", \"user\": { \"username\": \"ldrkic1\" } }";
        mockMvc.perform(post("/answer?questionID=3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User ldrkic1 isn't admin!"));
    }

    @Test
    public void deleteAnswer() throws Exception {
        String json = "{ \"answer\": \"odgvovor na pitanje\", \"user\": { \"username\": \"aadmin21\" } }";
        mockMvc.perform(post("/answer?questionID=3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/answer?id=3")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

}