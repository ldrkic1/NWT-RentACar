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
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    public void allReviews() throws Exception {
        mockMvc.perform(get("/review/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @BeforeAll
    public void getReviewById () throws Exception {
        mockMvc.perform( get("/review?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getReviewById2 () throws Exception {
        mockMvc.perform( get("/review?id=5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Review with id: 5 doesn't exist."));
    }

    @Test
    public void allClientReviews() throws Exception {
        mockMvc.perform(get("/review/client?clientID=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void allClientReviews2() throws Exception {
        mockMvc.perform(get("/review/client?clientID=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with id: 1 isn't client."));
    }

    @Test
    public void allClientReviews3() throws Exception {
        mockMvc.perform(get("/review/client?clientID=10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Client with id: 10 doesn't exist."));
    }

    @Test
    public void deleteReview() throws Exception {
        mockMvc.perform(delete("/review?id=1")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteReview2() throws Exception {
        mockMvc.perform(delete("/review?id=5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Review with id: 5 doesn't exist."));

    }

    @Test
    public void addReview() throws Exception {
        String json = "{ \"title\": \"Naslov\",\"review\": \"Tekst recenzije\", \"user\": { \"username\": \"ldrkic1\"} }";
        mockMvc.perform(post("/review/newReview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        String json1 = "{ \"title\": \"  \",\"review\": \"Tekst recenzije\", \"user\": { \"username\": \"ldrkic1\"} }";
        mockMvc.perform(post("/review/newReview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Title is required."));
        String json2 = "{ \"title\": \" naslov \",\"review\": \" \", \"user\": { \"username\": \"ldrkic1\"} }";
        mockMvc.perform(post("/review/newReview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Review is required."));
        String json3 = "{ \"title\": \" naslov \",\"review\": \"Tekst recenzije\", \"user\": { \"username\": \"ldrkic\"} }";
        mockMvc.perform(post("/review/newReview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cilent ldrkic doesn't exist."));
    }

}