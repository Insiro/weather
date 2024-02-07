package com.insiro.weather.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CityNotFoundException extends ApplicationException {
    private Long id = null;
    private String name = null;

    public CityNotFoundException(long id) {
        super(String.format("City Not Found ( id : %d )", id), HttpStatus.NOT_FOUND);
        this.id = id;
    }

    public CityNotFoundException(String name) {
        super(String.format("City Not Found ( name : %s )", name), HttpStatus.NOT_FOUND);
        this.name = name;
    }

}
