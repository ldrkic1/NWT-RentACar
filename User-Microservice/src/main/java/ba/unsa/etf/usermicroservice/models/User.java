package ba.unsa.etf.usermicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Data
@AllArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "First name is required!")
    @Pattern(regexp = "[A-Za-z \\s-]*", message = "First name is not valid")
    private String firstName;
    @NotBlank(message = "Last name is required!")
    @Pattern(regexp = "[A-Za-z \\s-]*", message = "Last name is not valid")
    private String lastName;
    @NotBlank(message = "Email is required!")
    @Email(message = "Email is not valid!")
    private String email;
    @NotBlank(message = "Username is required!")
    @Size(min = 1, max = 15, message = "Username must have between 1 and 15 characters!")
    private String username;
    @NotBlank(message = "Password is required!")
    @ValidPassword
    private String password;
    private Boolean enabled=Boolean.TRUE;
    private LocalDateTime lastActivity=LocalDateTime.now();

    //@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String email, String username, String password, Boolean enabled, LocalDateTime lastActivity) {
        System.out.println("BBBBBBBB");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled=enabled;
        this.lastActivity=lastActivity;
    }

    public User() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }
}
