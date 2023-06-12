package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Ruolo {
    @JsonProperty("superuser")
    SUPERUSER,
    @JsonProperty("customer")
    CUSTOMER
}
