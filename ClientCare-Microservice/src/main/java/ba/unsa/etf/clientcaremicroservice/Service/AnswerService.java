package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.*;
import ba.unsa.etf.clientcaremicroservice.Model.Answer;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Model.Role;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.AnswerRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.QuestionRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.RoleRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_ADMIN;
import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_CLIENT;

@Service
public class AnswerService {
    @Autowired
    private final AnswerRepository ansRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestTemplate restTemplate;

    public AnswerService(AnswerRepository ansRepository) {
        this.ansRepository = ansRepository;
    }

    public List<Answer> getAnswers() {
        return ansRepository.findAll();
    }

    public Answer getAnswerOnQuestion(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent()) {
            Optional<Answer> answer = ansRepository.getAnswerByQuestionId(questionId);
            if (answer.isPresent()) {
                return answer.get();
            } else throw new ApiRequestException("Question with id: " + questionId + " isn't answered!");
        }
        else throw new NotFoundException("Question with id: " + questionId + " doesn't exist.");
    }


    public Answer addAnswerOnQuestion(Answer answer, Long questionId) {
        Optional<Answer> ans = ansRepository.getAnswerByQuestionId(questionId);
        if(!ans.isPresent()) {
            Optional<Question> question = questionRepository.findById(questionId);
            if(question.isPresent()) {
                if(answer.getUser().getUsername().isEmpty() || answer.getUser().getUsername().isBlank() || answer.getUser().getUsername() == null) {
                    throw new ValidationException("Username is required");
                }
                if(answer.getUser().getUsername().length()<1 || answer.getUser().getUsername().length()>15) {
                    throw new ValidationException("Username size must be between 1 and 15 characters");
                }

                Optional<User> user = userRepository.getUserByUsername(answer.getUser().getUsername());
                if(user.isPresent()) {
                    if(!user.get().isClient()) {
                        if(answer.getAnswer() == null || answer.getAnswer().isEmpty() || answer.getAnswer().isBlank()) throw new ValidationException("Answer is required.");
                        answer.setUser(user.get());
                        answer.setQuestion(question.get());
                        question.get().setAnswered(true);
                        return ansRepository.save(answer);
                    }
                    else throw new ApiRequestException("User " + answer.getUser().getUsername()  + " isn't admin!");
                }
                else {
                    String url = "http://user-service/users/admin?username="+answer.getUser().getUsername();
                    try {
                        ResponseEntity<String> adminResponse = restTemplate.getForEntity(url, String.class);
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(adminResponse.getBody());
                        User admin = new User();
                        admin.setId(root.path("id").asLong());
                        admin.setFirstName(root.path("firstName").asText());
                        admin.setLastName(root.path("lastName").asText());
                        admin.setUsername(root.path("username").asText());
                        Set<Role> roles = new HashSet<>();
                        for(JsonNode jnode: root.get("roles")) {
                            Role role = new Role();
                            if(jnode.path("roleName").asText().equals("ROLE_CLIENT")) role = roleRepository.getRoleByRoleName(ROLE_CLIENT).get();
                            else role = roleRepository.getRoleByRoleName(ROLE_ADMIN).get();
                            roles.add(role);
                        }
                        if(answer.getAnswer() == null || answer.getAnswer().isEmpty() || answer.getAnswer().isBlank()) throw new ValidationException("Answer is required.");
                        admin.setRoles(roles);
                        userRepository.save(admin);
                        User createdAdmin = userRepository.getUserByUsername(admin.getUsername()).get();
                        answer.setUser(createdAdmin);
                        answer.setQuestion(question.get());
                        question.get().setAnswered(true);
                        return ansRepository.save(answer);

                    } catch(HttpClientErrorException e) {
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
            else throw new NotFoundException("Question with id: " + questionId + " doesn't exist.");
        }
        else throw new ApiRequestException("Question with id: " + questionId + " is answered. Answer id: " + ans.get().getId() + ".");
    }

    public String deleteAnswerById(Long answerId) {
        Optional<Answer> ans = ansRepository.findById(answerId);
        if(ans.isPresent()) {
            Optional<Question> question = questionRepository.findById(ans.get().getQuestion().getId());
            question.get().setAnswered(false);
            ansRepository.deleteById(answerId);
            return "Answer deleted successfully.";
        }
        else throw new NotFoundException("Answer with id: " + answerId + " doesn't exist.");
    }
}
