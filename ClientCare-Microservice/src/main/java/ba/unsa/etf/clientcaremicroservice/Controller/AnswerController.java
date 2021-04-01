package ba.unsa.etf.clientcaremicroservice.Controller;

import ba.unsa.etf.clientcaremicroservice.Model.Answer;
import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping(path = "/all")
    public List<Answer> getAnswers() {
        return answerService.getAnswers();
    }

    //odgovor na pitanje sa zadanim id
    @GetMapping
    Answer getAnswerOnQuestion(@RequestParam(value = "questionID") Long questionId) {
        return answerService.getAnswerOnQuestion(questionId);
    }

    //dodavanje odgovora na pitanje sa zadanim id
    @PostMapping
    public Answer addAnswerOnQuestion(@RequestBody Answer answer, @RequestParam(value = "questionID") Long questionId) {
        return answerService.addAnswerOnQuestion(answer,questionId);
    }

    //brisanje odgovora sa zadanim id
    @DeleteMapping
    public String deleteAnswerById(@RequestParam(value = "id") Long answerId) {
        return answerService.deleteAnswerById(answerId);
    }


}
