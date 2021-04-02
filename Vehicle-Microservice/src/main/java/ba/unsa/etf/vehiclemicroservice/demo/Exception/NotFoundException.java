package ba.unsa.etf.vehiclemicroservice.demo.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}