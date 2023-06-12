package com.example.demo.controller;

import com.example.demo.dto.UtenteDto;
import com.example.demo.entities.Stato;
import com.example.demo.exception.UtenteNotFoundException;
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
@RequestMapping("api/utente")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController {

    private final UtenteService utenteService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UtenteDto>> getUtenti() {
        log.info("ottenute liste utenti");
        List<UtenteDto> utenti = utenteService.getUtenti();

        if (utenti.isEmpty()) {
            return new ResponseEntity<List<UtenteDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UtenteDto>>(utenti, HttpStatus.OK);
    }

    @GetMapping(value = "/inattivi",produces = "application/json")
    public ResponseEntity<List<UtenteDto>> getUtentiNonAttivi() {
        log.info("ottenute liste utenti");
        List<UtenteDto> utenti = utenteService.getUtentiNonAttivi();

        if (utenti.isEmpty()) {
            return new ResponseEntity<List<UtenteDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UtenteDto>>(utenti, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity<UtenteDto> getUtente(@PathVariable("id") Long id) throws UtenteNotFoundException {
        log.info("Utente id:" + id);
        UtenteDto utente = utenteService.getUtente(id);

        if (utente == null) {
            //return new ResponseEntity<UtenteDto>(HttpStatus.NO_CONTENT);
            throw new UtenteNotFoundException("Utente inesistente e/o id errato");
        }
        return new ResponseEntity<UtenteDto>(utente, HttpStatus.OK);
    }

    @RequestMapping(value = "/inserisciModifica", produces = "application/json", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<UtenteDto> creaModificaUtente(@RequestBody UtenteDto utente) throws UtenteNotFoundException {
        log.info("Modifica Utente con id:" + utente.getId());
        utenteService.insModificaUtente(utente);
        return new ResponseEntity<UtenteDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/elimina/{id}")
    public ResponseEntity<?> eliminaUtente(@PathVariable("id") Long id) throws UtenteNotFoundException {
        log.info("eliminato utente con id:" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        UtenteDto utente = utenteService.getUtente(id);
        if (utente == null) {
            log.warn("Impossibile trovare utente con id:" + id);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new UtenteNotFoundException("Utente inesistente e/o id errato");
        }
        utenteService.deleteUtente(utente);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione utente " + id + " avvenuta con successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }


    @PutMapping (value = "/approva/{id}", produces = "application/json")
    public ResponseEntity<UtenteDto> approvaUtente(@PathVariable("id") Long id) throws UtenteNotFoundException {
        log.info("id Utente:"+id);
        utenteService.approvaUtente(id);

        return new ResponseEntity<UtenteDto>(new HttpHeaders(), HttpStatus.CREATED);
    }
}
