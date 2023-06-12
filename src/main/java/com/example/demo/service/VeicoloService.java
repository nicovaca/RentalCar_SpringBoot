package com.example.demo.service;

import com.example.demo.dto.VeicoloDto;
import com.example.demo.entities.Veicolo;
import com.example.demo.exception.VeicoliNotFoundException;

import java.util.List;

public interface VeicoloService {
    public List<VeicoloDto> getVeicoli();

    public VeicoloDto getVeicolo(Long id);

    public void insModificaVeicolo(VeicoloDto veicolo) throws VeicoliNotFoundException;

    public void deleteVeicolo(VeicoloDto veicolo);
}
