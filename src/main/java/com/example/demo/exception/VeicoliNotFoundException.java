package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeicoliNotFoundException extends Exception{
    private String messaggio;

    public VeicoliNotFoundException(String message) {
        super(message);
        this.messaggio=message;
    }

    public VeicoliNotFoundException() {
        super();
    }
}
