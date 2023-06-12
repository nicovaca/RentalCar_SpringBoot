package com.example.demo.repository;

import com.example.demo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public Utente getUtenteByUsername(String username);
}
