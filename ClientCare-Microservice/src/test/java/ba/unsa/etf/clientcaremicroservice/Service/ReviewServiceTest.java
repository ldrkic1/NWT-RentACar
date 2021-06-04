package ba.unsa.etf.clientcaremicroservice.Service;
import ba.unsa.etf.clientcaremicroservice.Exception.ApiRequestException;
import ba.unsa.etf.clientcaremicroservice.Exception.NotFoundException;
import ba.unsa.etf.clientcaremicroservice.Exception.ValidationException;
import ba.unsa.etf.clientcaremicroservice.Model.Review;
import ba.unsa.etf.clientcaremicroservice.Model.Role;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Test
    public void getAllReviewsTest() {
        assertEquals(2, reviewService.getReviews(null).size());
        assertEquals(1, reviewService.getReviews("Odlična vozila").size());
    }

    @Test
    public void getClientReviewsTest() {
        assertDoesNotThrow(() -> reviewService.getClientReviews(4L));
        assertThrows(
                ApiRequestException.class,
                () -> reviewService.getClientReviews(7L));
        assertThrows(
                NotFoundException.class,
                () -> reviewService.getClientReviews(900L));
    }

    @Test
    public void getReviewByIdTest() {
        assertThrows(
                NotFoundException.class,
                () -> reviewService.getReviewById(9L));
        assertDoesNotThrow(() -> reviewService.getReviewById(1L));
    }

    @Test
    public void addReviewTest() {
        Review review = new Review();
        User user = userService.getUserById(7L).get();
        review.setUser(user);
        //user nije klijent
        assertThrows(
                ValidationException.class,
                () -> reviewService.addReview(review));
        //klijent ne postoji
        user = new User();
        user.setFirstName("Lala");
        user.setLastName("Lalic");
        user.setUsername("llalic123");
        review.setUser(user);
        assertThrows(
                NotFoundException.class,
                () -> reviewService.addReview(review));
        user = userService.getUserById(4L).get();
        review.setUser(user);
        assertThrows(
                ValidationException.class,
                () -> reviewService.addReview(review));
        review.setTitle("Naslov");
        assertThrows(
                ValidationException.class,
                () -> reviewService.addReview(review));
        review.setReview("Recenzijaa");
        assertDoesNotThrow(() -> reviewService.addReview(review));
        assertEquals(3, reviewService.getReviews(null).size());
        reviewService.deleteReviewById(3L);
    }

    @Test
    public void addReviewCommunicationTest() throws JsonProcessingException {
        int sizeBefore=userRepository.findAll().size();
        Review review = new Review();
        review.setTitle("Naslov");
        review.setReview("Recenzijaa");
        User user=new User();
        user.setUsername("ldrkic1");
        review.setUser(user);
        assertDoesNotThrow(() -> reviewService.addReview(review));
        //vec postoji user pa velicina ostaje ista
        assertEquals(sizeBefore, userRepository.findAll().size());
        user.setUsername("irma");
        review.setUser(user);
        reviewService.addReview(review);
        assertEquals(sizeBefore+1, userRepository.findAll().size());
        reviewService.deleteReviewById(4L);
    }

    @Test
    public void deleteReviewTest() {
        assertThrows(
                NotFoundException.class,
                () -> reviewService.deleteReviewById(9L));

        assertDoesNotThrow(() -> reviewService.deleteReviewById(1L));
        assertEquals(1, reviewService.getReviews(null).size());
    }

}