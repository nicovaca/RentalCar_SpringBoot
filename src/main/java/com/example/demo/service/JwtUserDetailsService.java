package com.example.demo.service;

import com.example.demo.dto.UtenteDto;
import com.example.demo.entities.Ruolo;
import com.example.demo.entities.Stato;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log
@CrossOrigin("http://localhost:4200")
public class JwtUserDetailsService implements UserDetailsService {

    private final UtenteService utenteService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		/*if ("admin".equals(username)) {
			return new User("admin", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}*/

        UtenteDto utenteDto = utenteService.getUtenteByUsername(username);
        if (utenteDto == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (utenteDto.getStato() == Stato.IN_ATTIVAZIONE){
            throw new UsernameNotFoundException("L'utente non è ancora stato approvato");
        }
        User.UserBuilder builder = User.withUsername(utenteDto.getUsername());
        builder.password(utenteDto.getPassword());
        if (utenteDto.getRuolo() == Ruolo.SUPERUSER) {
            builder.roles("superuser");
        } else builder.roles("customer");
        return builder.build();
    }
}

