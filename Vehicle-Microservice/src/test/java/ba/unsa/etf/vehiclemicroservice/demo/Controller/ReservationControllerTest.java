package ba.unsa.etf.vehiclemicroservice.demo.Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allReservations() throws Exception {
        mockMvc.perform(get("/reservation/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void getReservationById2 () throws Exception {
        mockMvc.perform( get("/reservation?id=7")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Reservation with id: 7 doesn't exist."));
    }
    @Test
    public void getReservationById() throws Exception {
        mockMvc.perform( get("/reservation?id=2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brojRezervacije").value(1232));
    }
    @Test
    public void addReservation() throws Exception {
        String json1 = "{\n" +
                "        \"reservationStart\": \"2021-03-20\",\n" +
                "        \"reservationEnd\": \"2021-03-21\",\n" +
                "        \"registered\": {\n" +
                "            \"firstName\": \"Niko\",\n" +
                "            \"lastName\": \"Nikic\"\n" +
                "        },\n" +
                "        \"vehicle\": {\n" +
                "            \"model\": \"BMW\",\n" +
                "            \"category\": {\n" +
                "                \"description\": \"Putnicka\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"carShare\": true,\n" +
                "        \"brojRezervacije\": 6232\n" +
                "}";
        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isOk());
        String json ="{\n" +
                "        \"reservationStart\": \"2021-03-20\",\n" +
                "        \"reservationEnd\": \"2021-03-20\",\n" +
                "        \"registered\": {\n" +
                "            \"firstName\": \"Niko\",\n" +
                "            \"lastName\": \"Nikic\"\n" +
                "        },\n" +
                "        \"vehicle\": {\n" +
                "            \"model\": \"BMW\",\n" +
                "            \"category\": {\n" +
                "                \"description\": \"Putnicka\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"carShare\": true,\n" +
                "        \"brojRezervacije\": 6232\n" +
                "}";
        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Ending date can't be the same as the starting date"));
       String json2 ="{\n" +
               "        \"reservationStart\": \"2021-03-20\",\n" +
               "        \"reservationEnd\": \"2021-03-20\",\n" +
               "        \"registered\": {\n" +
               "            \"firstName\": \"Niko\",\n" +
               "            \"lastName\": \"Nikic\"\n" +
               "        },\n" +
               "        \"carShare\": true,\n" +
               "        \"brojRezervacije\": 6232\n" +
               "}";
        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No vehicle"));
        String json3 ="{\n" +
                "        \"reservationStart\": \"2021-03-20\",\n" +
                "        \"reservationEnd\": \"2021-03-20\",\n" +
                "        \"vehicle\": {\n" +
                "            \"model\": \"BMW\",\n" +
                "            \"category\": {\n" +
                "                \"description\": \"Putnicka\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"carShare\": true,\n" +
                "        \"brojRezervacije\": 6232\n" +
                "}";
        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No registered user"));

    }
}