package com.example.demo.service;

import com.example.demo.dto.PrenotazioneDto;
import com.example.demo.exception.PrenotazioniNotFoundException;

import java.util.List;

public interface PrenotazioneService {
    public List<PrenotazioneDto> getPrenotazioni();
    public PrenotazioneDto getPrenotazione(Long id);
    public void insModificaPrenotazione(PrenotazioneDto prenotazione) throws PrenotazioniNotFoundException;

    public void deletePrenotazione(PrenotazioneDto prenotazione);

    public List<PrenotazioneDto> getPrenotazioniUtente(Long utente_id);

}
