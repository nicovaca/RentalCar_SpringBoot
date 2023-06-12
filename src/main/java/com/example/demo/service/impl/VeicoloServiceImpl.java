package com.example.demo.service.impl;

import com.example.demo.dto.VeicoloDto;
import com.example.demo.entities.Veicolo;
import com.example.demo.exception.VeicoliNotFoundException;
import com.example.demo.repository.VeicoloRepository;
import com.example.demo.service.VeicoloService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VeicoloServiceImpl implements VeicoloService {

    private final VeicoloRepository veicoloRepository;
    private final ModelMapper mapper;

    @Override
    public List<VeicoloDto> getVeicoli() {

        List<Veicolo> veicoli = veicoloRepository.findAll();

        List<VeicoloDto> veicoliDto = veicoli
                .stream()
                .map(source -> mapper.map(source, VeicoloDto.class))
                .collect(Collectors.toList());

        return veicoliDto;
    }

    @Override
    public VeicoloDto getVeicolo(Long id) {
        Veicolo veicolo =  veicoloRepository.getVeicoloById(id);
        VeicoloDto veicoloDto = null;
        if (veicolo!=null){
            veicoloDto = mapper.map(veicolo, VeicoloDto.class);
        }
        return veicoloDto;
    }

    @Override
    public void insModificaVeicolo(VeicoloDto veicoloDto) throws VeicoliNotFoundException {
        if (veicoloDto.getId() == null){
            Veicolo veicolo = mapper.map(veicoloDto, Veicolo.class);
            veicoloRepository.save(veicolo);
        } else {
            VeicoloDto veicoloDaModificare = this.getVeicolo(veicoloDto.getId());
            if (veicoloDaModificare != null){
                Veicolo veicolo = mapper.map(veicoloDto, Veicolo.class);
                veicoloRepository.save(veicolo);
            }else
                throw new VeicoliNotFoundException("Il veicolo non esiste");
        }

    }

    @Override
    public void deleteVeicolo(VeicoloDto veicoloDto) {
        Veicolo veicolo = mapper.map(veicoloDto, Veicolo.class);
        veicoloRepository.delete(veicolo);
    }
}
