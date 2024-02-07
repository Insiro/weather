package com.insiro.weather.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApplicationException extends RuntimeException {
    final protected long timestamp;
    final protected String message;
    final protected HttpStatus status;
    protected ApplicationException(String message, HttpStatus status){
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.status = status;
    }
}
