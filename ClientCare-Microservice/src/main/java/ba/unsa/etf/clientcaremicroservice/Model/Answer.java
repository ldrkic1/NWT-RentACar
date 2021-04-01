package ba.unsa.etf.clientcaremicroservice.Model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Entity
@Table
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Answer is required")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Answer() {

    }

    public Answer(String answer, User user, Question question) {
        this.answer = answer;
        this.user = user;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
