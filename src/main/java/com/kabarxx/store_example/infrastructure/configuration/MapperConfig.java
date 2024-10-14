package com.kabarxx.store_example.infrastructure.configuration;

import com.kabarxx.store_example.infrastructure.mappers.CartMapper;
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
