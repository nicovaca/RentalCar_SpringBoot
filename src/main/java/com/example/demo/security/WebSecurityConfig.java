package com.example.demo.security;

import com.example.demo.security.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    private final UserDetailsService jwtUserDetailsService;


    private final JwtRequestFilter jwtRequestFilter;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }


    private static final String[] ADMIN_MATCHER =
            {
                    //"api/utente",
                    "api/utente/id/{id}",
                  //  "api/utente/inserisciModifica",
                    "api/utente/elimina/{id}",
                    "api/utente/inattivi",
                    "api/veicoli/id/{id}",
                    "api/veicoli/inserisciModifica",
                    "api/veicoli/elimina/{id}",
                    "api/prenotazioni",
                    "api/prenotazioni/id/{id}",
                    "api/prenotazioni/inserisciModifica",
                    "api/prenotazioni/elimina/{id}",
                    "api/prenotazioni/approva/{id}",

            };

    private static final String[] CUSTOMER_MATCHER =
            {
                    "api/utente/prenotazioniCustomer/{id}",
                    "api/prenotazioni/inserisciModifica"
            };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable()

                .authorizeRequests().antMatchers("/api/authenticate","/api/veicoli","/api/utente/inserisciModifica").permitAll()
                .and().authorizeRequests()
                .antMatchers(ADMIN_MATCHER).access("hasRole('SUPERUSER')")
                .antMatchers(CUSTOMER_MATCHER).access("hasRole('CUSTOMER')")
                .anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
