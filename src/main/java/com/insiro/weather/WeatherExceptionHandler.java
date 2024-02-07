package com.insiro.weather;

import com.insiro.weather.exception.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Data
@Builder
@AllArgsConstructor
class ErrorBody {
    long timestamp;
    int status;
    String message;

    public ErrorBody(ApplicationException exception) {
        this.timestamp = exception.getTimestamp();
        this.message = exception.getMessage();
        this.status = exception.getStatus().value();
    }

}

@RestControllerAdvice
public class WeatherExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorBody> handleAllExceptions(Exception ex) {
        ErrorBody body = ErrorBody.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage()).build();


        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorBody> handleApplicationException(ApplicationException ex) {
        ErrorBody body = new ErrorBody(ex);
        return new ResponseEntity<>(body, ex.getStatus());
    }
}
