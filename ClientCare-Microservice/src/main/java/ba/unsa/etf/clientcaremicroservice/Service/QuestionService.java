package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.*;
import ba.unsa.etf.clientcaremicroservice.Model.Review;
import ba.unsa.etf.clientcaremicroservice.Model.Role;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.QuestionRepository;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Repository.RoleRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_ADMIN;
import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_CLIENT;

@Service
public class QuestionService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestions(String title) {
        if(title == null) return questionRepository.findAll();
        else return questionRepository.getAllQuestionsByTitle(title);
    }
    public List<Question> getAllUnansweredQuestions() {
        return questionRepository.getUnansweredQuestions();
    }

    public List<Question> getAllAnsweredQuestions() {
        return questionRepository.getAnsweredQuestions();
    }

    public List<Question> getClientQuestions(Long clientID) {
        Optional<User> user = userRepository.findById(clientID);
        if(user.isPresent()) {
            if(user.get().isClient()) return questionRepository.findAllByUserId(clientID);
            else throw new ApiRequestException("User with id: " + clientID + " isn't client.");
        }
        else throw new NotFoundException("Client with id: " + clientID + " doesn't exist.");

    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Question with id: " + questionId + " doesn't exist."));
    }
    public String deleteQuestionById(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent()) {
            if(question.get().isAnswered()) {
                questionRepository.deleteQuestionsAnswer(question.get().getId());
            }
            questionRepository.deleteById(questionId);
            return "Question deleted successfully.";
        }
        else {
            throw new NotFoundException("Question with id: " + questionId + " doesn't exist.");
        }
    }
    public Question addQuestion(Question question) {
        if(question.getUser().getUsername().isEmpty() || question.getUser().getUsername().isBlank() || question.getUser().getUsername() == null) {
            throw new ValidationException("Username is required");
        }
        if(question.getUser().getUsername().length()<1 || question.getUser().getUsername().length()>15) {
            throw new ValidationException("Username size must be between 1 and 15 characters");
        }

        Optional<User> user = userRepository.getUserByUsername(question.getUser().getUsername());
        //user je spasen jer je vec postavljao pitanja
        if(user.isPresent()) {
            if(user.get().isClient()) {
                if(question.getTitle() == null || question.getTitle().isEmpty() || question.getTitle().isBlank()) throw new ValidationException("Title is required.");
                if(question.getQuestion() == null || question.getQuestion().isEmpty() || question.getQuestion().isBlank()) throw new ValidationException("Question is required.");
                question.setUser(user.get());
                return questionRepository.save(question);
            }
            else throw new ApiRequestException(user.get().getUsername() + " isn't client.");
        }
        //trazimo usera od user servisa
        else {
            String url = "http://user-service/users/client?username="+question.getUser().getUsername();
            try {
                ResponseEntity<String> userResponse = restTemplate.getForEntity(url, String.class);
                //status je OK - user postoji
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(userResponse.getBody());
                User newUser = new User();
                newUser.setId(root.path("id").asLong());
                newUser.setLastName(root.path("lastName").asText());
                newUser.setUsername(root.path("username").asText());
                newUser.setFirstName(root.path("firstName").asText());
                newUser.setLastName(root.path("lastName").asText());
                newUser.setUsername(root.path("username").asText());
                Set<Role> roles = new HashSet<>();
                for(JsonNode jnode: root.get("roles")) {
                    Role role = new Role();
                    if(jnode.path("roleName").asText().equals("ROLE_CLIENT")) role = roleRepository.getRoleByRoleName(ROLE_CLIENT).get();
                    else role = roleRepository.getRoleByRoleName(ROLE_ADMIN).get();
                    roles.add(role);
                }

                newUser.setRoles(roles);
                if(question.getTitle() == null || question.getTitle().isEmpty() || question.getTitle().isBlank()) throw new ValidationException("Title is required.");
                if(question.getQuestion() == null || question.getQuestion().isEmpty() || question.getQuestion().isBlank()) throw new ValidationException("Question is required.");
                userRepository.save(newUser);
                User createdUser = userRepository.getUserByUsername(newUser.getUsername()).get();
                question.setUser(createdUser);
                return questionRepository.save(question);
            }  catch (HttpClientErrorException e) {
                //user ne postoji u bazi user servisa
                JSONObject json = new JSONObject(e.getResponseBodyAsString());
                throw new NotFoundException(json.get("message").toString());
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
