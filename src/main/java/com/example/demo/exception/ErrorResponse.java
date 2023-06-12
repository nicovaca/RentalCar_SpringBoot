package com.example.demo.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private int codice;
    private String messaggio;
}
