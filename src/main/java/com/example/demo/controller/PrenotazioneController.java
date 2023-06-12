package com.example.demo.controller;

import com.example.demo.dto.PrenotazioneDto;
import com.example.demo.dto.UtenteDto;
import com.example.demo.exception.PrenotazioniNotFoundException;
import com.example.demo.exception.UtenteNotFoundException;
import com.example.demo.service.PrenotazioneService;
import com.example.demo.service.UtenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prenotazioni")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class PrenotazioneController {
    
    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PrenotazioneDto>> getPrenotazioni(){
        log.info("ottenute liste Prenotazioni");
        List<PrenotazioneDto> prenotazioni = prenotazioneService.getPrenotazioni();

        if (prenotazioni.isEmpty()){
            return new ResponseEntity<List<PrenotazioneDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PrenotazioneDto>>(prenotazioni, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity<PrenotazioneDto> getPrenotazioneById(@PathVariable("id") Long id) throws PrenotazioniNotFoundException {
        log.info("Prenotazione id:" +id);
        PrenotazioneDto prenotazione = prenotazioneService.getPrenotazione(id);
        log.info("dataInizio:"+prenotazione.getDataInizio()+", dataFine:"+prenotazione.getDataFine());
        if (prenotazione == null){
            //return new ResponseEntity<PrenotazioneDto>(HttpStatus.NO_CONTENT);
            throw new PrenotazioniNotFoundException("Prenotazione inesistente e/o id errato");
        }
        return new ResponseEntity<PrenotazioneDto>(prenotazione, HttpStatus.OK);
    }

    @RequestMapping(value = "/inserisciModifica", produces = "application/json", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<PrenotazioneDto> creaModificaPrenotazione(@RequestBody PrenotazioneDto prenotazione) throws PrenotazioniNotFoundException {
        log.info("Modifica Prenotazione con id:" + prenotazione.getId());
        log.info("dataInizio:"+prenotazione.getDataInizio()+", dataFine:"+prenotazione.getDataFine());
        prenotazioneService.insModificaPrenotazione(prenotazione);
        return new ResponseEntity<PrenotazioneDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/elimina/{id}")
    public ResponseEntity<?> eliminaPrenotazione(@PathVariable("id") Long id) throws PrenotazioniNotFoundException {
        log.info("eliminato prenotazione con id:" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        PrenotazioneDto prenotazione = prenotazioneService.getPrenotazione(id);
        if (prenotazione == null) {
            log.warn("Impossibile trovare la prenotazione con id:" + id);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new PrenotazioniNotFoundException("Prenotazione inesistente e/o id errato");

        }
        prenotazioneService.deletePrenotazione(prenotazione);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione prenotazione " + id + " avvenuta con successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/prenotazioniCustomer/{id}", produces = "application/json")
    public ResponseEntity<List<PrenotazioneDto>> getPrenotazioniUtente(@PathVariable("id") Long id) throws PrenotazioniNotFoundException, UtenteNotFoundException {
        log.info("id Utente:"+id);
        UtenteDto u = utenteService.getUtente(id);
        List<PrenotazioneDto> prenotazioni = prenotazioneService.getPrenotazioniUtente(id);
        if (prenotazioni.isEmpty()){
            return new ResponseEntity<List<PrenotazioneDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PrenotazioneDto>>(prenotazioni, HttpStatus.OK);
    }

    @RequestMapping(value = "/approva/{id}", produces = "application/json",method = {RequestMethod.PUT})
    public ResponseEntity<PrenotazioneDto> approvaPrenotazione(@PathVariable("id") Long id) throws PrenotazioniNotFoundException, UtenteNotFoundException {
        log.info("id Prenotazione:"+id);
        PrenotazioneDto prenotazioneDto = prenotazioneService.getPrenotazione(id);

        if (prenotazioneDto == null) {
            log.warn("Impossibile trovare la prenotazione con id:" + id);
            throw new PrenotazioniNotFoundException("Prenotazione inesistente e/o id errato");
        }

        prenotazioneDto.setApprovazione(true);
        prenotazioneService.insModificaPrenotazione(prenotazioneDto);

        return new ResponseEntity<PrenotazioneDto>(new HttpHeaders(), HttpStatus.CREATED);
    }
}

