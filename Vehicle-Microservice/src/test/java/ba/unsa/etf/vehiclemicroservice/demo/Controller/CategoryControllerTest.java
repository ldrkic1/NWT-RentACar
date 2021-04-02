package ba.unsa.etf.vehiclemicroservice.demo.Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allCategories() throws Exception {
        mockMvc.perform(get("/category/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryById () throws Exception {
        mockMvc.perform( get("/category?id=2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Transportna"));
    }

    @Test
    public void getCategoryById2 () throws Exception {
        mockMvc.perform( get("/category?id=7")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category with id: 7 doesn't exist."));
    }
    @Test
    public void createCategory() throws Exception {
        String json1 = "malo";
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isOk());
    }
    @Test
    public void categoryByDescription() throws Exception {
        mockMvc.perform(get("/category/bydescription?description=malo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}