package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public void save(Question question) {

        questionRepository.save(question);
        System.out.println("*****"+question.getId());
    }
    public List<QuestionNotification> getQuestion(Long id) {
        return questionRepository.findAllByQuestionId(id);
    }
}
