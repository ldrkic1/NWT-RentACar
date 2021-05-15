package ba.unsa.etf.notificationmicroservice.DTO;

import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.User;

public class QuestionDTO {
    private Question question;
    private User user;

    public QuestionDTO(Question question, User user) {
        this.question = question;
        this.user = user;
    }

    public QuestionDTO() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
