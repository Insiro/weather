package com.insiro.weather.exception;

import lombok.Getter;

@Getter
public class CityNameConflictException extends ApplicationException {
    private final String cityName;

    public CityNameConflictException(String cityName) {
        super(String.format("City Name is Conflicted : %s", cityName));
        this.cityName = cityName;
    }
}
