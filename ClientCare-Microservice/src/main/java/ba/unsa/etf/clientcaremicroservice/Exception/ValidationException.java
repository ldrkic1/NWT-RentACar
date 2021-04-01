package ba.unsa.etf.clientcaremicroservice.Exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
