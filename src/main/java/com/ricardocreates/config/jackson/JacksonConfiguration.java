package com.ricardocreates.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {
    @Bean
    @Primary
    public ObjectMapper getJacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(
                com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                false
        );
        return objectMapper;
    }
}
