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
@AllArgsConstructor
@Builder
class ErrorBody {
    long timestamp;
    int status;
    String message;
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
        String msg = String.format("Weather Not Found (id : %d )", ex.getId());
        ErrorBody body = ErrorBody.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .message(msg).build();
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorBody> handleCityNotFoundException(CityNotFoundException ex) {
        String what = (ex.getId() == null) ? "name : " + ex.getName() : String.format("id : %d", ex.getId());
        String msg = String.format("City Not Found ( %s )", what);
        ErrorBody body = ErrorBody.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .message(msg).build();
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityNameConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorBody> handleCityNameConflictException(CityNameConflictException ex) {
        ErrorBody body = ErrorBody.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.CONTINUE.value())
                .message(String.format("City Name is Conflicted : %s", ex.getCityName()))
                .build();
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
