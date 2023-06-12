package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "utente")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Utente  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome")
    private String cognome;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dataNascita")
    private Date dataNascita;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ruolo")
    private Ruolo ruolo;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "stato")
    private Stato stato;


    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonBackReference
    private Set<Prenotazione> prenotazioni = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Utente utente = (Utente) o;
        return getId() != null && Objects.equals(getId(), utente.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}