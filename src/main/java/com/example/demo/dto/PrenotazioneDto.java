package com.example.demo.dto;

import com.example.demo.entities.Utente;
import com.example.demo.entities.Veicolo;
import lombok.Data;

import java.util.Date;
@Data
public class PrenotazioneDto {

    private Long id;
    private Date dataInizio;
    private Date dataFine;
    private boolean approvazione;
    private Utente utente;
    private Veicolo veicolo;
}
