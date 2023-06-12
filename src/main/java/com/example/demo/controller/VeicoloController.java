package com.example.demo.controller;

import com.example.demo.dto.VeicoloDto;
import com.example.demo.exception.VeicoliNotFoundException;
import com.example.demo.service.VeicoloService;
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
@RequestMapping("api/veicoli")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class VeicoloController {

    private final VeicoloService veicoloService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<VeicoloDto>> getVeicoli() {
        log.info("ottenute liste veicoli");
        List<VeicoloDto> veicoli = veicoloService.getVeicoli();

        if (veicoli.isEmpty()) {
            return new ResponseEntity<List<VeicoloDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<VeicoloDto>>(veicoli, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity<VeicoloDto> getVeicoloById(@PathVariable("id") Long id) throws VeicoliNotFoundException {
        log.info("veicolo id:" + id);
        VeicoloDto veicolo = veicoloService.getVeicolo(id);

        if (veicolo == null) {
           //return new ResponseEntity<VeicoloDto>(HttpStatus.NO_CONTENT);
            throw new VeicoliNotFoundException("Veicolo inesistente e/o id errato");
        }
        return new ResponseEntity<VeicoloDto>(veicolo, HttpStatus.OK);
    }



    @DeleteMapping(value = "/elimina/{id}")
    public ResponseEntity<?> eliminaVeicolo(@PathVariable("id") Long id) throws VeicoliNotFoundException {
        log.info("eliminato veicolo con id:" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        VeicoloDto veicolo = veicoloService.getVeicolo(id);
        if (veicolo == null) {
            log.warn("Impossibile trovare veicolo con id:" + id);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new VeicoliNotFoundException("Veicolo inesistente e/o id errato");
        }
        veicoloService.deleteVeicolo(veicolo);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione veicolo " + id + " avvenuta con successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/inserisciModifica", produces = "application/json", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<VeicoloDto> creaModificaVeicolo(@RequestBody VeicoloDto veicolo) throws VeicoliNotFoundException {
        log.info("Modifica Veicolo con id:" + veicolo.getId());
        veicoloService.insModificaVeicolo(veicolo);
        return new ResponseEntity<VeicoloDto>(new HttpHeaders(), HttpStatus.CREATED);
    }


}
