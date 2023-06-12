package com.example.demo;

import com.example.demo.entities.Veicolo;
import com.example.demo.repository.VeicoloRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = SpringBootRentalCarApplication.class)
public class testFindByIdVeicolo {

    @Autowired
    private VeicoloRepository veicoloRepository;

    @Test
    public void testByIdVeicolo(){
        Long id = 1L;

        assertThat(veicoloRepository.findById(id)).get()
                .extracting(Veicolo::getModello)
                .isEqualTo("Giulietta");
    }
}
