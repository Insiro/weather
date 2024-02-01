package com.insiro.weather.exception;


import lombok.Getter;

@Getter
public class WeatherNotFoundException extends RuntimeException {
    private final long id;
    public WeatherNotFoundException(long id) {
        this.id = id;
    }

}
