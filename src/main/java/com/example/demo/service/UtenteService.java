package com.example.demo.service;

import com.example.demo.dto.UtenteDto;
import com.example.demo.entities.Utente;
import com.example.demo.exception.UtenteNotFoundException;

import java.util.List;

public interface UtenteService {

     List<UtenteDto> getUtenti();
     UtenteDto getUtente(Long id) throws UtenteNotFoundException;

     void insModificaUtente(UtenteDto utente) throws UtenteNotFoundException;

     void deleteUtente(UtenteDto utente);

    UtenteDto getUtenteByUsername(String username);

    List<UtenteDto> getUtentiNonAttivi();
    void approvaUtente(Long id) throws UtenteNotFoundException;
}
