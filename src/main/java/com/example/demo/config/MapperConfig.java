package com.example.demo.config;

import com.example.demo.dto.PrenotazioneDto;
import com.example.demo.entities.Prenotazione;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.addMappings(prenotazioneMapping);
        mapper.addMappings(periodoPrenotazioneMapping);
        mapper.addConverter(Converter);

        return mapper;
    }

    PropertyMap<Prenotazione, PrenotazioneDto> prenotazioneMapping = new PropertyMap<Prenotazione, PrenotazioneDto>() {
        @Override
        protected void configure() {
            map().setDataInizio(source.getPeriodoPrenotazione().getDataInizio());
            map().setDataFine(source.getPeriodoPrenotazione().getDataFine());
        }
    };

    PropertyMap<PrenotazioneDto, Prenotazione> periodoPrenotazioneMapping = new PropertyMap<PrenotazioneDto, Prenotazione>() {
        @Override
        protected void configure() {
            map().getPeriodoPrenotazione().setDataInizio(source.getDataInizio());
            map().getPeriodoPrenotazione().setDataFine(source.getDataFine());
        }
    };

    Converter<String,String> Converter = new Converter<String, String>() {
        @Override
        public String convert(MappingContext<String, String> mappingContext) {
            return mappingContext.getSource() == null ? "" : mappingContext.getSource().trim();
        }
    };


}
