package com.example.demo.service.impl;

import com.example.demo.dto.PrenotazioneDto;
import com.example.demo.entities.Prenotazione;
import com.example.demo.exception.PrenotazioniNotFoundException;
import com.example.demo.repository.PrenotazioneRepository;
import com.example.demo.service.PrenotazioneService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final ModelMapper mapper;

    @Override
    public List<PrenotazioneDto> getPrenotazioni() {

        List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        return prenotazioni
                .stream()
                .map(source -> mapper.map(source, PrenotazioneDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PrenotazioneDto getPrenotazione(Long id) {

        Prenotazione prenotazione = prenotazioneRepository.findById(id).orElse(null);
        PrenotazioneDto prenotazioneDto = null;
        if (prenotazione != null) {
            prenotazioneDto = mapper.map(prenotazione, PrenotazioneDto.class);
        }
        return prenotazioneDto;
    }

    @Override
    public void insModificaPrenotazione(PrenotazioneDto prenotazioneDto) throws PrenotazioniNotFoundException {
        if (prenotazioneDto.getId() == null){
            Prenotazione prenotazione = mapper.map(prenotazioneDto, Prenotazione.class);
            prenotazioneRepository.save(prenotazione);
        } else {
            PrenotazioneDto prenotazioneDaModificare = this.getPrenotazione(prenotazioneDto.getId());
            if (prenotazioneDaModificare != null){
                Prenotazione prenotazione = mapper.map(prenotazioneDto, Prenotazione.class);
                prenotazioneRepository.save(prenotazione);
            }else
                throw new PrenotazioniNotFoundException("La prenotazione non esiste");
        }
    }

    @Override
    public void deletePrenotazione(PrenotazioneDto prenotazioneDto) {
        Prenotazione prenotazione = mapper.map(prenotazioneDto, Prenotazione.class);
        prenotazioneRepository.delete(prenotazione);
    }

    @Override
    public List<PrenotazioneDto> getPrenotazioniUtente(Long utente_id) {
        List<Prenotazione> prenotazioni = prenotazioneRepository.getPrenotazioneByUtenteId(utente_id);
        return prenotazioni
                .stream()
                .map(source -> mapper.map(source, PrenotazioneDto.class))
                .collect(Collectors.toList());
    }


}
