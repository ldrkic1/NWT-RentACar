package ba.unsa.etf.clientcaremicroservice.Service;

import ba.unsa.etf.clientcaremicroservice.Exception.ApiRequestException;
import ba.unsa.etf.clientcaremicroservice.Exception.NotFoundException;
import ba.unsa.etf.clientcaremicroservice.Exception.ValidationException;
import ba.unsa.etf.clientcaremicroservice.Model.Review;
import ba.unsa.etf.clientcaremicroservice.Model.User;
import ba.unsa.etf.clientcaremicroservice.Repository.ReviewRepository;
import ba.unsa.etf.clientcaremicroservice.Repository.UserRepository;
import ba.unsa.etf.clientcaremicroservice.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Review addReview(Review review) {
        Optional<User> user = userRepository.findUserByFirstNameAndAndLastName(review.getUser().getFirstName(), review.getUser().getLastName());
        if(user.isPresent()) {
            if(user.get().isClient()) {
                if (review.getTitle() == null || review.getTitle().isEmpty() || review.getTitle().isBlank())
                    throw new ValidationException("Title is required.");
                if (review.getReview() == null || review.getReview().isEmpty() || review.getReview().isBlank())
                    throw new ValidationException("Review is required.");
                review.setUser(user.get());
                return reviewRepository.save(review);
            }

            else throw new ApiRequestException(review.getUser().getFirstName() + " " + review.getUser().getLastName() + " isn't client.");
        }
        else throw new NotFoundException("Cilent " + review.getUser().getFirstName() + " " + review.getUser().getLastName() + " doesn't exist.");

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
