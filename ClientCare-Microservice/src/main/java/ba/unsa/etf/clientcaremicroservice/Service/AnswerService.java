package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.*;
import ba.unsa.etf.clientcaremicroservice.Model.Answer;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.AnswerRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.QuestionRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_ADMIN;

@Service
public class AnswerService {
    @Autowired
    private final AnswerRepository ansRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    public AnswerService(AnswerRepository ansRepository) {
        this.ansRepository = ansRepository;
    }

    public List<Answer> getAnswers() {
        return ansRepository.findAll();
    }

    public Answer getAnswerOnQuestion(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent()) {
            Optional<Answer> answer = ansRepository.findAnswerByQuestion_Id(questionId);
            if (answer.isPresent()) {
                return answer.get();
            } else throw new ApiRequestException("Question with id: " + questionId + " isn't answered!");
        }
        else throw new NotFoundException("Question with id: " + questionId + " doesn't exist.");
    }


    public Answer addAnswerOnQuestion(Answer answer, Long questionId) {
        Optional<Answer> ans = ansRepository.findAnswerByQuestion_Id(questionId);
        if(!ans.isPresent()) {
            Optional<Question> question = questionRepository.findById(questionId);
            if(question.isPresent()) {
                Optional<User> user = userRepository.findUserByFirstNameAndAndLastName(answer.getUser().getFirstName(), answer.getUser().getLastName());
                if(user.isPresent()) {
                    if(!user.get().isClient()) {
                        if(answer.getAnswer() != null && !answer.getAnswer().isEmpty() && !answer.getAnswer().isBlank()) {
                            answer.setUser(user.get());
                            answer.setQuestion(question.get());
                            question.get().setAnswered(true);
                            return ansRepository.save(answer);
                        }
                        else throw new ValidationException("Answer is required.");
                    }
                    else throw new ApiRequestException("User " + answer.getUser().getFirstName() + " " + answer.getUser().getLastName() + " isn't admin!");
                }
                else throw new NotFoundException("User " + answer.getUser().getFirstName() + " " + answer.getUser().getLastName() + " doesn't exist");
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
