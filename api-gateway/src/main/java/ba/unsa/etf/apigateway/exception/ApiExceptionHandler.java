package ba.unsa.etf.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        //create payload
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, ZonedDateTime.now(ZoneId.of("Z")));
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }
}