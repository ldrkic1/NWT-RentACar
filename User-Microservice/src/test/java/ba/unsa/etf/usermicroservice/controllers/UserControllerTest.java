package ba.unsa.etf.usermicroservice.controllers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allUsers() throws Exception {
        mockMvc.perform(get("/users/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void allClients() throws Exception {
        mockMvc.perform(get("/users/clients")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception{
        mockMvc.perform(get("/users/user?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        //id usera ne postoji
        mockMvc.perform(get("/users/user?id=900"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 900 doesn't exist."));
    }
    @Test
    public void getUserByUsername() throws Exception{
        mockMvc.perform(get("/users/byUsername?username=idedic")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("idedic"));

        //username usera ne postoji
        mockMvc.perform(get("/users/byUsername?username=nepostojeci"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with username: nepostojeci doesn't exist."));
    }

    @Test
    public void getUsersByRole() throws Exception{
        mockMvc.perform(get("/users/byRole?role_name=ROLE_CLIENT")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/byRole?role_name=NEPOSTOJECA"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Role with role name: NEPOSTOJECA doesn't exist."));

    }

    @Test
    public void getRolesByUser() throws Exception{
        mockMvc.perform(get("/users/roles?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //username usera ne postoji
        mockMvc.perform(get("/users/roles?id=990"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 990 doesn't exist."));

    }
    @Test
    public void addUser() throws Exception {
        //sve okej
        String json = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"novi\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        //username zauzet
        String json1 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"novi\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username is already taken"));
        //ime prazno
        String json2 = "{ \"firstName\": \"\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"druga\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("First name is empty"));
        //prezime prazno
        String json3 = "{ \"firstName\": \"Irma\",\"lastName\": \"\", \"email\": \"irma@gmail.com\", \"username\": \"novi2\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Last name is empty"));
        //email prazno
        String json4 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"\", \"username\": \"novi3\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json4))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Email is empty"));
        //email nevalidan
        String json5 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"nevalidni\", \"username\": \"novi4\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json5))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Not valid email"));
        //username prazan
        String json6 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json6))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Username is empty"));
        //username nevalidan
       String json7 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"asdfghjasdfghasdfs\", \"password\": \"1VarijacijA1**\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json7))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Username size must be between 1 and 15 characters"));
        String json8 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"asdfg\", \"password\": \"\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
        mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json8))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Password is empty"));
        String json9 = "{ \"firstName\": \"Irma\",\"lastName\": \"Dedic\", \"email\": \"irma@gmail.com\", \"username\": \"asdfg\", \"password\": \"neispravno\", \"enabled\": \"true\", \"lastActivity\": \"2015-04-06T23:59:30\", \"roles\": [{ \"roleName\": \"ROLE_CLIENT\"}] }";
       /* mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json9))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Password not in accordance with password policy."));*/
    }
    @Test
    public void delete2() throws Exception {
        mockMvc.perform(delete("/users?id=900")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with id: 900 doesn't exist."));

    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users?id=2")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }



}
