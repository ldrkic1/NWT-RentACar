package ba.unsa.etf.clientcaremicroservice.Model;

import ba.unsa.etf.clientcaremicroservice.RoleName;

import javax.persistence.*;

@Entity
@Table
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoleName roleName;

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
