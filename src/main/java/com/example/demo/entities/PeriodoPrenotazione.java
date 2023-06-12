package com.example.demo.entities;


import javax.persistence.*;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Embeddable
@Access(AccessType.FIELD)
@Data
public class PeriodoPrenotazione {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dataInizio")
    private Date dataInizio;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dataFine")
    private Date dataFine;
}
