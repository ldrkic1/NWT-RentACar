package ba.unsa.etf.apigateway.service;

import ba.unsa.etf.apigateway.RoleName;
import ba.unsa.etf.apigateway.model.AuthenticationRequest;
import ba.unsa.etf.apigateway.model.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return new User("foo", "foo", new ArrayList<>());
        //dobavimo usere iz user servisa i pronadjemo onog sa username-om username
        RestTemplate restTemplate1=new RestTemplate();
        System.out.println("u load sam" + username);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        System.out.println("prije poziva");
        ResponseEntity<String> resp = restTemplate1.getForEntity("http://localhost:8085/users/userDTO?username=" + username, String.class);
        System.out.println("poslije poziva");
        AuthenticationRequest response = new AuthenticationRequest();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(resp.getBody());
            response.setId(root.path("id").asLong());
            response.setUsername(root.path("username").asText());
            response.setPassword(root.path("password").asText());
            JsonNode rootRoles = root.path("roles");
            Set<Role> roleSet = new HashSet<>();
            rootRoles.forEach(role -> {
                System.out.println("roleeee"+role.path("id").asLong() + " " + role.path("roleName").asText());
                roleSet.add(new Role(role.path("roleName").asText().equals("ROLE_CLIENT") ? RoleName.ROLE_CLIENT : RoleName.ROLE_ADMIN));


            });
            response.setRola(roleSet);
            System.out.println(response.getUsername());
            System.out.println(response.getPassword());
            System.out.println(getGrantedAuthorities(response.getRola()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if(resp.getBody() == null){
            throw new UsernameNotFoundException("User sa username-om " + username + " nije pronadjen!");
        }
        //username, sifra, list of authorities
        return new User(response.getUsername(),response.getPassword(), getGrantedAuthorities(response.getRola()));
    }
    private Collection<GrantedAuthority> getGrantedAuthorities(Set<Role> rola){
        System.out.println("u granteeed: ");
        for(Role r: rola)
            System.out.println(r.getRoleName().toString());
        Collection<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for(Role role: rola)
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
        return grantedAuthorities;
    }
}
