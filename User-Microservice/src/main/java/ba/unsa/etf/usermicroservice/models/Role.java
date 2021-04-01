package ba.unsa.etf.usermicroservice.models;

import ba.unsa.etf.usermicroservice.RoleName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role(RoleName roleAdmin) {
        this.roleName=roleAdmin;
    }

    public Role() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    //@JsonValue
    public RoleName getRoleName() {
        return roleName;
    }
    //@JsonValue
    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
