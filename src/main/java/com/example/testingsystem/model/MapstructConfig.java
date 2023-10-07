package com.example.testingsystem.model;

import lombok.Builder;
import org.hibernate.boot.model.internal.XMLContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapstructConfig {
//    @Bean
//    public DefaultMapperProcessor mapperProcessor() {
//        return new DefaultMapperProcessor();
//    }

    @Bean
    public ModelMapper getMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
