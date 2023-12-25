package com.epam.victor.config;

import com.epam.victor.storage.util.MapDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.epam.victor")
@PropertySource("classpath:booking.properties")
public class SpringConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MapDeserializer mapDeserializer = new MapDeserializer();
        mapDeserializer.setObjectMapper(objectMapper);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Map.class, mapDeserializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
