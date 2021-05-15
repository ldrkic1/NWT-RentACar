package ba.unsa.etf.clientcaremicroservice.DTO;

import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Model.User;

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
