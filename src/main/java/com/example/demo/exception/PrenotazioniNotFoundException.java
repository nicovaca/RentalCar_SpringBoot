package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrenotazioniNotFoundException extends Exception{
    private String messaggio;

    public PrenotazioniNotFoundException(String message) {
        super(message);
        this.messaggio=message;
    }

    public PrenotazioniNotFoundException() {
        super();
    }
}
