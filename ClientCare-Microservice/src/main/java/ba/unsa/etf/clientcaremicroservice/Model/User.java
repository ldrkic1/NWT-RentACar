package ba.unsa.etf.clientcaremicroservice.Model;
import ba.unsa.etf.clientcaremicroservice.RoleName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isClient() {
        for(Role role: roles) {

            if(role.getRoleName().equals(RoleName.ROLE_CLIENT)) return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                '}';
    }
}
