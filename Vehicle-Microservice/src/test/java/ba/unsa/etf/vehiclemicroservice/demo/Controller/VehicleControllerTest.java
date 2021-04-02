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
public class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void allVehicles() throws Exception {
        mockMvc.perform(get("/vehicle/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getVehicleById() throws Exception {
        mockMvc.perform( get("/vehicle?id=2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Toyota"));
    }

    @Test
    public void allVehiclesOfCategory() throws Exception {
        mockMvc.perform(get("/vehicle/category?id=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void deleteVehicle() throws Exception {
        mockMvc.perform(delete("/vehicle?id=4")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteVehicle2() throws Exception {
        mockMvc.perform(delete("/vehicle?id=9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehicle with id: 9 doesn't exist."));
    }

    @Test
    public void addVehicle() throws Exception {
        String json = "{ \"model\": \"Seat\",\"brojSjedista\": 5, \"potrosnja\": 10, \"category\": { \"description\": \"Putnicka\" } }";

        mockMvc.perform(post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

    }

    @Test
    public void editVehicle() throws Exception {
        String json = "{ \"model\": \"Novi BMW\",\"brojSjedista\": 5, \"potrosnja\": 10, \"category\": { \"description\": \"Putnicka\" } }";
        mockMvc.perform(put("/vehicle?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Novi BMW"));

    }
    @Test
    public void addVehicle2() throws Exception {
        String json = "{ \"model\": \"\",\"brojSjedista\": 5, \"potrosnja\": 10, \"category\": { \"description\": \"Putnicka\" } }";

        mockMvc.perform(post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Model is required"));

    }
    @Test
    public void addVehicle3() throws Exception {
        String json = "{ \"model\": \"Model\",\"brojSjedista\": 1, \"potrosnja\": 10, \"category\": { \"description\": \"Putnicka\" } }";

        mockMvc.perform(post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Number of seats can't be less than 2!"));

    }
}