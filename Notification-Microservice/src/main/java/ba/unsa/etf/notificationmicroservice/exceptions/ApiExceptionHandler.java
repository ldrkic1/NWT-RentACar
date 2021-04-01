package ba.unsa.etf.notificationmicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value={ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest=HttpStatus.BAD_REQUEST;
        ApiException apiException=new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(value={ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleApiRequestException(ValidationException e){
        HttpStatus badRequest=HttpStatus.NOT_ACCEPTABLE;
        ApiException apiException=new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(value={NotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(NotFoundException e){
        HttpStatus badRequest=HttpStatus.NOT_FOUND;
        ApiException apiException=new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
}
