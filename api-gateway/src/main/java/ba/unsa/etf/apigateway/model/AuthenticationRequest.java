package ba.unsa.etf.apigateway.model;

import java.util.Set;

public class AuthenticationRequest {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;

    public AuthenticationRequest() {
    }

    /*public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }*/

    public AuthenticationRequest(Long id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Role> getRola() {
        return roles;
    }

    public void setRola(Set<Role> rola) {
        this.roles = rola;
    }
}
