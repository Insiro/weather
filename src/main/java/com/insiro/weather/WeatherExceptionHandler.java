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

    @Builder(builderMethodName = "fromException")
    ErrorBody(ApplicationException exception, int status) {
        this.timestamp = exception.getTimestamp();
        this.message = exception.getMessage();
        this.status = status;
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

    @ExceptionHandler(WeatherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorBody> handleWeatherNotFoundException(WeatherNotFoundException ex) {
        ErrorBody body = ErrorBody.fromException()
                .exception(ex)
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorBody> handleCityNotFoundException(CityNotFoundException ex) {
        ErrorBody body = ErrorBody.fromException()
                .status(HttpStatus.NOT_FOUND.value())
                .exception(ex)
                .build();
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityNameConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorBody> handleCityNameConflictException(CityNameConflictException ex) {
        ErrorBody body = ErrorBody.fromException()
                .status(HttpStatus.CONTINUE.value())
                .exception(ex)
                .build();
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
