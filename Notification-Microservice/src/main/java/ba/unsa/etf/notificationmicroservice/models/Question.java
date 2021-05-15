package ba.unsa.etf.notificationmicroservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table
public class Question {
    @Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;

    /*@OneToMany(mappedBy="question")
    private Set<QuestionNotification> questionNotifications;*/
    @JsonBackReference
    @OneToOne(mappedBy = "question")
    private QuestionNotification questionNotification;

    public Question() {
        questionNotification = new QuestionNotification();
    }
    public Question(QuestionNotification questionNotification) {
        this.questionNotification = questionNotification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionNotification getQuestionNotification() {
        return questionNotification;
    }

    public void setQuestionNotification(QuestionNotification questionNotification) {
        this.questionNotification = questionNotification;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionNotification=" + questionNotification +
                '}';
    }
}
