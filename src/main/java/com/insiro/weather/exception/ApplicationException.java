package com.insiro.weather.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    final protected long timestamp;
    protected String message;
    protected ApplicationException(String message){
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }
}
