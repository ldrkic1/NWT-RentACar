package ba.unsa.etf.apigateway.controller;


import ba.unsa.etf.apigateway.RoleName;
import ba.unsa.etf.apigateway.exception.ValidationException;
import ba.unsa.etf.apigateway.model.AuthenticationResponse;
import ba.unsa.etf.apigateway.model.Role;
import ba.unsa.etf.apigateway.service.MyUserDetailsService;
import ba.unsa.etf.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ba.unsa.etf.apigateway.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwt;

    @RequestMapping({"/hello"})
    public String hello(){
        return "hello world";
    }

    @PostMapping(value="/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception {
        System.out.println("*****");
        System.out.println(authReq.getUsername());
        System.out.println(authReq.getPassword());
        System.out.println("*****");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        }

        catch(BadCredentialsException e){
            throw new ValidationException("Incorrect username or password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authReq.getUsername());
        final String token = jwt.generateToken(userDetails);
        final Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        List<Role> roleList = new ArrayList<>();

        for (GrantedAuthority g:roles) {
            System.out.println(g.getAuthority());
            roleList.add(new Role(RoleName.valueOf(g.getAuthority())));
        }
        final Set<Role> role = new HashSet<>(roleList);
        return ResponseEntity.ok(new AuthenticationResponse(token, role));

    }
}
