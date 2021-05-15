package ba.unsa.etf.clientcaremicroservice.Controller;
import ba.unsa.etf.clientcaremicroservice.DTO.QuestionDTO;
import ba.unsa.etf.clientcaremicroservice.RabbitMQ.Config;
import ba.unsa.etf.clientcaremicroservice.Service.QuestionService;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping(path = "/all")
    public List<Question> getQuestions(@RequestParam(value = "title", required = false) String title) {
        return questionService.getQuestions(title);
    }

    @GetMapping(path = "/unanswered")
    public List<Question> getUnansweredQuestions() {
        return questionService.getAllUnansweredQuestions();
    }
    @GetMapping(path = "/answered")
    public List<Question> getAnsweredQuestions() {
        return questionService.getAllAnsweredQuestions();
    }

    //klijentova pitanja
    @GetMapping(path = "/client")
    public List<Question> getClientQuestions(@RequestParam(value = "clientID") Long clientID) { return questionService.getClientQuestions(clientID); }

    //pitanje po id
    @GetMapping
    public Question getQuestionById(@RequestParam(value = "id") Long questionId) {
        return questionService.getQuestionById(questionId);
    }

    //brisanje pitanja
    @DeleteMapping
    public String deleteQuestionById(@RequestParam(value = "id") Long questionId) {
        return questionService.deleteQuestionById(questionId);
    }

    //dodavanje novog pitanja
    @PostMapping(path = "/newQuestion")
    public Question addQuestion(@RequestBody Question question) {
        //Producer producer = null;
        Question q =questionService.addQuestion(question);
        //producer.send(q.getId().toString());
        QuestionDTO questionDTO=new QuestionDTO(q, q.getUser());
        rabbitTemplate.convertAndSend(Config.EXCHANGE, Config.ROUTING_KEY,questionDTO);
        return q;
    }

}
