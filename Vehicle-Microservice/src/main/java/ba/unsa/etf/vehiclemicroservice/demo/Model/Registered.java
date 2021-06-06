package ba.unsa.etf.vehiclemicroservice.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class Registered {
    @Id
    private Long  id;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Username is required!")
    @Size(min = 1, max = 15, message = "Username must have between 1 and 15 characters!")
    private String username;

    public Registered() {
    }

    public Registered(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                '}';
    }
}
