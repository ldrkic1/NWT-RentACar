package ba.unsa.etf.apigateway.config;

import ba.unsa.etf.apigateway.filter.JwtRequestFilter;
import ba.unsa.etf.apigateway.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;


@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
        cors().and()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/users/users/all", "/users/users/clients", "/users/users/admins", "/users/users/user", "/users/users/byUsername", "/users/users/userDTO", "/users/users/client", "/users/users/admin", "/users/users/byRole", "/users/users/roles", "/users/users/countUsers", "/users/users/countClients").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/authenticate", "/users/users/newUser").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/users/updateUser").hasAnyRole("ADMIN", "CLIENT")

                .antMatchers(HttpMethod.GET, "/notifications/notifications/all", "/notifications/notifications/client", "/notifications/notifications/notification", "/notifications/notifications/between").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/notifications/questionNotifications/newQuestionNotification", "/notifications/reservationNotifications/newReservationNotification").permitAll()
                .antMatchers(HttpMethod.GET, "/notifications/questionNotifications/all", "/notifications/questionNotifications/client", "/notifications/questionNotifications/question", "/notifications/questionNotifications/questionNotification", "/notifications/questionNotifications/between").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/notifications/reservationNotifications/all", "/notifications/reservationNotifications/client", "/notifications/reservationNotifications/reservation", "/notifications/reservationNotifications/reservationNotification", "/notifications/reservationNotifications/between").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"/clientcares/review/all","/clientcares/question/all","/clientcares/answer/all").permitAll()
                //.antMatchers(HttpMethod.POST,"/clientcares/review/newReview","/clientcares/question/newQuestion").hasRole("CLIENT")
                .antMatchers(HttpMethod.POST,"/clientcares/review/newReview","/clientcares/question/newQuestion").permitAll()
                .antMatchers(HttpMethod.DELETE,"/clientcares/review","/clientcares/question","/clientcares/answer").permitAll()
                .antMatchers(HttpMethod.GET,"/clientcares/question/unanswered","/clientcares/question/answered","/clientcares/review","/clientcares/question","/clientcares/answer").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/clientcares/answer").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"/vehicles/vehicle/all","/vehicles/vehicle/category","/vehicles/vehicle").permitAll()
                .antMatchers(HttpMethod.DELETE,"/vehicles/vehicle").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/vehicles/vehicle").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/vehicles/vehicle").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/vehicles/reservation").hasAnyRole("ADMIN","CLIENT")
                .antMatchers(HttpMethod.GET,"/vehicles/reservation/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/vehicles/reservation").hasRole("CLIENT")

                .anyRequest().authenticated()
                .and()
                //.cors().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
