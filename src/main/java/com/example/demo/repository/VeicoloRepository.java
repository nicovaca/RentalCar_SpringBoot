package com.example.demo.repository;

import com.example.demo.dto.VeicoloDto;
import com.example.demo.entities.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeicoloRepository extends JpaRepository<Veicolo, Long> {
    Veicolo getVeicoloById(Long id);
}
