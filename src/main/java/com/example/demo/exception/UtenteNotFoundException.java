package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteNotFoundException extends Exception{
    private String messaggio;

    public UtenteNotFoundException(String message) {
        super(message);
        this.messaggio=message;
    }

    public UtenteNotFoundException() {
        super();
    }
}
