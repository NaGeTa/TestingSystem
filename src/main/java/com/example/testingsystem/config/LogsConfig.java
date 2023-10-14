package com.example.testingsystem.config;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogsConfig {
    LogsConfig(){
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }
}
