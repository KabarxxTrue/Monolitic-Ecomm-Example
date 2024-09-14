package com.kabarxx.store_example.configuration;

import com.kabarxx.store_example.mappers.CartMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public CartMapper cartMapper() {
        return Mappers.getMapper(CartMapper.class);
    }
}
