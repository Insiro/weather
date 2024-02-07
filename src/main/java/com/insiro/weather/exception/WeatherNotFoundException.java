package com.insiro.weather.exception;


import lombok.Getter;

@Getter
public class WeatherNotFoundException extends ApplicationException {
    private final long id;
    public WeatherNotFoundException(long id) {
        super(String.format("Weather Not Found (id : %d )", id));
        this.id = id;
    }

}
