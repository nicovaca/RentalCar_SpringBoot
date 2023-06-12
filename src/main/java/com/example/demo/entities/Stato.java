package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Stato {
    @JsonProperty("inAttivazione")
    IN_ATTIVAZIONE,
    @JsonProperty("attivo")
    ATTIVO
}
