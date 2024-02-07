package com.insiro.weather.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WeatherNotFoundException extends ApplicationException {
    private final long id;

    public WeatherNotFoundException(long id) {
        super(String.format("Weather Not Found (id : %d )", id), HttpStatus.NOT_FOUND);
        this.id = id;
    }

}
