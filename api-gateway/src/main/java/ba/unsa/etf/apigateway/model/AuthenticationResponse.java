package ba.unsa.etf.apigateway.model;

import java.util.Set;

public class AuthenticationResponse {
    private String jwt;
    private Set<Role> roles;

    public AuthenticationResponse(String jwt, Set<Role> roles) {
        this.jwt = jwt;
        this.roles = roles;
    }

    /*
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }*/

    public String getJwt() {
        return jwt;
    }

    public Set<Role> getRole() {
        return roles;
    }
}
