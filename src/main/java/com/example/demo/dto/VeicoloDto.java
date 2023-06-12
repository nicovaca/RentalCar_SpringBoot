package com.example.demo.dto;

import com.example.demo.entities.TipoVeicolo;
import lombok.Data;

@Data
public class VeicoloDto {


    private Long id;
    private String casaCostruttrice;
    private String modello;
    private TipoVeicolo tipoVeicolo;
    private Integer annoImmatricolazione;
    private String targa;
}
