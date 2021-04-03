package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.*;
import ba.unsa.etf.clientcaremicroservice.Model.Review;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.QuestionRepository;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_CLIENT;

@Service
public class QuestionService {
    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

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
        if(user.isPresent()) {
            if(user.get().isClient()) {
                if(question.getTitle() == null || question.getTitle().isEmpty() || question.getTitle().isBlank()) throw new ValidationException("Title is required.");
                if(question.getQuestion() == null || question.getQuestion().isEmpty() || question.getQuestion().isBlank()) throw new ValidationException("Question is required.");
                question.setUser(user.get());
                return questionRepository.save(question);
            }
            else throw new ApiRequestException(user.get().getUsername() + " isn't client.");
        }
        else throw new NotFoundException("Client " + question.getUser().getUsername() + " doesn't exist.");
    }
}
