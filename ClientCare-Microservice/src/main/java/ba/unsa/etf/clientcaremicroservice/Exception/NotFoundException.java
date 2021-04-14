package ba.unsa.etf.clientcaremicroservice.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {

    }
}
