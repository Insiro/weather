package com.insiro.weather.exception;

import lombok.Getter;

@Getter
public class CityNameConflictException extends RuntimeException{
    private final String cityName;
    public  CityNameConflictException(String cityName){
        this.cityName = cityName;
    }
}
