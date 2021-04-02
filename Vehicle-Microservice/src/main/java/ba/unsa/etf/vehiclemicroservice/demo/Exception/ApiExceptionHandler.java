package ba.unsa.etf.vehiclemicroservice.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        //create payload
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }
    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        //create payload
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, ZonedDateTime.now(ZoneId.of("Z")));
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        //create payload
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("Z")));
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }
}
