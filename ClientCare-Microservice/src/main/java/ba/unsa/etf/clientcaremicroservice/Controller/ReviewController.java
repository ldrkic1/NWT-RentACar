package ba.unsa.etf.clientcaremicroservice.Controller;

import ba.unsa.etf.clientcaremicroservice.Model.Review;
import ba.unsa.etf.clientcaremicroservice.Service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    //sve recenzije ili sve sa naslovom
    @GetMapping("/all")
    public List<Review> getReviews(@RequestParam(value = "title", required = false) String title) {
        return reviewService.getReviews(title);
    }
    //klijentove recenzije
    @GetMapping(path = "/client")
    public List<Review> getClientReviews(@RequestParam(value = "clientID") Long clientID) { return reviewService.getClientReviews(clientID); }
    //recenzija po id ili title
    @GetMapping
    public Review getReviewById(@RequestParam(value = "id") Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }
    //brisanje recenzije
    @DeleteMapping
    public String deleteReviewById(@RequestParam(value = "id") Long reviewId) {
        return reviewService.deleteReviewById(reviewId);
    }
    //dodavanje nove recenzije
    @PostMapping(path = "/newReview")
    public Review addReview(@RequestBody Review review) throws JsonProcessingException {
        return reviewService.addReview(review);
    }


}
