package ba.unsa.etf.vehiclemicroservice.demo.Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CarShareTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allCarShare() throws Exception {
        mockMvc.perform(get("/carshare/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCarShareById2() throws Exception {
        mockMvc.perform(get("/carshare?id=7")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Car sharing with id: 7 doesn't exist."));
    }

    @Test
    public void deleteCarShare() throws Exception {
        mockMvc.perform(delete("/carshare/1")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }
    @Test
    public void createCarShare() throws Exception {
        String json1 = "{\n" +
                "    \"numberOfFreeSpaces\": 3\n" +
                "}";
        mockMvc.perform(post("/carshare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isOk());
    }
}