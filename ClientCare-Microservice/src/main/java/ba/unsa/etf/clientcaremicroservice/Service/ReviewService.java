package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.*;
import ba.unsa.etf.clientcaremicroservice.Model.Review;
import ba.unsa.etf.clientcaremicroservice.Model.Role;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.ReviewRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.aspectj.weaver.ast.Not;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_ADMIN;
import static ba.unsa.etf.clientcaremicroservice.RoleName.ROLE_CLIENT;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;
    /*public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }*/


    @Autowired
    private RestTemplate restTemplate;

    public List<Review> getReviews(String title) {

        if(title == null) return reviewRepository.findAll();
        else return reviewRepository.getAllByTitle(title);
    }

    public List<Review> getClientReviews(Long clientID) {
        Optional<User> user = userRepository.findById(clientID);
        if(user.isPresent()) {

            if(user.get().isClient()) {
                return reviewRepository.findAllByUserId(clientID);
            }
            else throw new ApiRequestException("User with id: " + clientID + " isn't client.");
        }
        else throw new NotFoundException("Client with id: " + clientID + " doesn't exist.");
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review with id: " + reviewId + " doesn't exist."));
    }

    public Review addReview(Review review) throws JsonProcessingException {
        if(review.getUser().getUsername().isEmpty() || review.getUser().getUsername().isBlank() || review.getUser().getUsername() == null) {
            throw new ValidationException("Username is required");
        }
        if(review.getUser().getUsername().length()<1 || review.getUser().getUsername().length()>15) {
            throw new ValidationException("Username size must be between 1 and 15 characters");
        }
        if(userRepository.findByUsername(review.getUser().getUsername()).isPresent()){
            review.setUser(userRepository.findByUsername(review.getUser().getUsername()).get());
        }
        else {
            try {
                System.out.println("********* TRY *******");


                String resourceURL = "http://user-service/users/client?username=" + review.getUser().getUsername();
                ResponseEntity<String> response = restTemplate.getForEntity(resourceURL, String.class);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                System.out.println("********* FROM SERVICE *******");
                System.out.println(root);
                User user = new User();
                user.setId(root.path("id").asLong());
                user.setFirstName(root.path("firstName").asText());
                user.setLastName(root.path("lastName").asText());
                user.setUsername(root.path("username").asText());


                JsonNode rootRoles = root.path("roles");
                Set<Role> roleSet = new HashSet<>();
                rootRoles.forEach(role -> {
                    System.out.println(role.path("id").asLong() + " " + role.path("roleName").asText());
                    roleSet.add(new Role(role.path("id").asLong(), (role.path("roleName").asText().equals("ROLE_CLIENT") ? ROLE_CLIENT : ROLE_ADMIN)));

                    //roleSet.add(new Role(role.path("id").asLong(), RoleName.ROLE_CLIENT ));

                });


                user.setRoles(roleSet);
                System.out.println("ID: " + user.getId());
                System.out.println("**********");

                //review.setUser(user);
                userRepository.save(user);
                review.setUser(userRepository.findByUsername(user.getUsername()).get());

            } catch (HttpClientErrorException exception) {
                //throw new ValidationException(exception.getLocalizedMessage());
                System.out.println("***** Exception");
                JSONObject json=new JSONObject(exception.getResponseBodyAsString());
                throw new NotFoundException(json.get("message").toString());
            }
        }

                if (review.getTitle() == null || review.getTitle().isEmpty() || review.getTitle().isBlank())
                    throw new ValidationException("Title is required.");
                if (review.getReview() == null || review.getReview().isEmpty() || review.getReview().isBlank())
                    throw new ValidationException("Review is required.");
                return reviewRepository.save(review);
    }

    public String deleteReviewById(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if(review.isPresent()) {
            reviewRepository.deleteById(reviewId);
            return "Review deleted successfully!";
        }
        else {
            throw new NotFoundException("Review with id: " + reviewId + " doesn't exist.");
        }
    }
}
