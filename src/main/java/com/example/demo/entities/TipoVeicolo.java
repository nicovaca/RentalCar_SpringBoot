package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoVeicolo {
    @JsonProperty("auto")
    AUTO,
    @JsonProperty("minivan")
    MINIVAN,
    @JsonProperty("furgone")
    FURGONE,
    @JsonProperty("suv")
    SUV;


}
