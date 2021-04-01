package ba.unsa.etf.notificationmicroservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class QuestionNotification extends Notification {

    /*@ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;*/
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;


    public QuestionNotification() {
    }

    public QuestionNotification(Question question) {
        this.question = question;
    }

    public QuestionNotification(String title, String content, LocalDateTime createdAt, User user, Question question) {
        super(title, content, createdAt, user);
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
