package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(VeicoliNotFoundException.class)
    public ResponseEntity<ErrorResponse> VeicoliNotFoundHandler(Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodice(HttpStatus.FOUND.value());
        errorResponse.setMessaggio(e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<ErrorResponse> UtenteNotFoundHandler(Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodice(HttpStatus.FOUND.value());
        errorResponse.setMessaggio(e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrenotazioniNotFoundException.class)
    public ResponseEntity<ErrorResponse> PrenotazioniNotFoundHandler(Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodice(HttpStatus.FOUND.value());
        errorResponse.setMessaggio(e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
