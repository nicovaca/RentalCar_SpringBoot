package com.example.demo.entities;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "prenotazione")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private PeriodoPrenotazione periodoPrenotazione;

    @Column(name = "approvazione")
    private boolean approvazione;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUtente", referencedColumnName = "id")
    //@JsonIgnore
    private Utente utente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVeicolo", referencedColumnName = "id")
    //@JsonIgnore
    private Veicolo veicolo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Prenotazione that = (Prenotazione) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
