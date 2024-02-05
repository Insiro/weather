package com.insiro.weather.exception;

import lombok.Getter;

@Getter
public class CityNotFoundException extends RuntimeException {
    private Long id = null;
    private String name = null;
    public CityNotFoundException(long id) {
        this.id = id;
    }
    public CityNotFoundException(String name){this.name = name;}

}
