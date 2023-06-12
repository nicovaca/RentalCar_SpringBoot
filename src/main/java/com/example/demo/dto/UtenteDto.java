package com.example.demo.dto;

import com.example.demo.entities.Ruolo;
import com.example.demo.entities.Stato;
import lombok.Data;


import java.util.Date;

@Data
public class UtenteDto {

    private Long id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String username;
    private String password;
    private Ruolo ruolo;
    private Stato stato;
}
