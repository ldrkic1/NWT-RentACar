package ba.unsa.etf.apigateway.model;

import ba.unsa.etf.apigateway.RoleName;

public class Role {
    private Integer id;
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
    public RoleName getRoleName() {
        return roleName;
    }
    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
