package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "veicolo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Veicolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "casaCostruttrice")
    private String casaCostruttrice;
    @Column(name = "modello")
    private String modello;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoVeicolo")
    private TipoVeicolo tipoVeicolo;
    @Column(name = "annoImmatricolazione")
    private Integer annoImmatricolazione;
    @Column(name = "targa")
    private String targa;


    @OneToMany(mappedBy = "veicolo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonBackReference
    private Set<Prenotazione> prenotazioni = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Veicolo veicolo = (Veicolo) o;
        return getId() != null && Objects.equals(getId(), veicolo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
