package ba.unsa.etf.clientcaremicroservice.Model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Entity
@Table
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Naslov pitanja je obavezan")
    private String title;

    @Column
    @NotBlank(message = "Pitanje je obavezno")
    private String question;

    @Column
    private boolean answered;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable =false)
    private User user;

    public Question() {
        answered = false;
    }

    public Question(String title, String question, User user) {
        this.title = title;
        this.question = question;
        this.user = user;
        answered = false;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", user_id=" + user.toString() +
                '}';
    }
}
