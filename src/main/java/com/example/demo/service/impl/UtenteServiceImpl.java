package com.example.demo.service.impl;

import com.example.demo.dto.UtenteDto;
import com.example.demo.entities.Stato;
import com.example.demo.entities.Utente;
import com.example.demo.exception.UtenteNotFoundException;
import com.example.demo.repository.UtenteRepository;
import com.example.demo.service.UtenteService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public List<UtenteDto> getUtenti() {

        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
                .filter(utente -> utente.getStato()== Stato.ATTIVO)
                .map(source -> mapper.map(source, UtenteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UtenteDto> getUtentiNonAttivi() {

        List<Utente> utenti = utenteRepository.findAll();
            return utenti.stream()
                    .filter(utente -> utente.getStato()== Stato.IN_ATTIVAZIONE)
                    .map(source -> mapper.map(source, UtenteDto.class))
                    .collect(Collectors.toList());

    }

    @Override
    public UtenteDto getUtente(Long id) {

        Utente utente =  utenteRepository.findById(id).orElse(null);
        UtenteDto utenteDto = null;
        if (utente!=null){
            utenteDto = mapper.map(utente, UtenteDto.class);
        }
        return utenteDto;
    }

    @Override
    public void insModificaUtente(UtenteDto utenteDto) throws UtenteNotFoundException {
        if (utenteDto.getId() == null){
            Utente utente = mapper.map(utenteDto, Utente.class);
            if (utenteDto.getPassword()!=null)
                utente.setPassword(bCryptPasswordEncoder.encode(utenteDto.getPassword()));
            utenteRepository.save(utente);
        }else {
            UtenteDto utenteDaModificare = this.getUtente(utenteDto.getId());
            if (utenteDaModificare != null){
                Utente utente = mapper.map(utenteDto, Utente.class);
                if (utenteDto.getPassword()!=null)
                    utente.setPassword(bCryptPasswordEncoder.encode(utenteDto.getPassword()));
                utenteRepository.save(utente);
            }else
                throw new UtenteNotFoundException("L'Utente non esiste");
        }

    }

    @Override
    public void deleteUtente(UtenteDto utenteDto) {
        Utente utente = mapper.map(utenteDto, Utente.class);
        utenteRepository.delete(utente);
    }

    @Override
    public UtenteDto getUtenteByUsername(String username) {
        Utente utente =  utenteRepository.getUtenteByUsername(username);
        UtenteDto utenteDto = null;
        if (utente!=null){
            utenteDto = mapper.map(utente, UtenteDto.class);
        }
        return utenteDto;
    }

    @Override
    public void approvaUtente(Long id) throws UtenteNotFoundException {
        Utente utente = utenteRepository.findById(id).orElse(null);
        if (utente!=null){
            utente.setStato(Stato.ATTIVO);
            utenteRepository.save(utente);

        }else
            throw new UtenteNotFoundException("L'Utente non esiste");
    }
}
