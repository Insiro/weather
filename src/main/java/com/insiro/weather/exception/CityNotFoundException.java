package com.insiro.weather.exception;

import lombok.Getter;

@Getter
public class CityNotFoundException extends ApplicationException {
    private Long id = null;
    private String name = null;

    public CityNotFoundException(long id) {
        super(String.format("City Not Found ( id : %d )", id));
        this.id = id;
    }

    public CityNotFoundException(String name) {
        super(String.format("City Not Found ( name : %s )", name));
        this.name = name;
    }

}
