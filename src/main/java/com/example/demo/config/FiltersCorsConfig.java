package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FiltersCorsConfig implements WebMvcConfigurer
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:4200") //url di angular
                        .allowedMethods("PUT","DELETE","GET","POST","OPTIONS","HEAD","PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(false)

                ;
            }
        };
    }
}
