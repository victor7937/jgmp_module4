package com.epam.victor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.epam.victor")
@PropertySource("classpath:booking.properties")
public class SpringConfig {

}
