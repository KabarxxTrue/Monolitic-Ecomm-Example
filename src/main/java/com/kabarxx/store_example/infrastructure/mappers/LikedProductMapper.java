package com.kabarxx.store_example.infrastructure.mappers;

import com.kabarxx.store_example.domain.models.LikedProduct;
import com.kabarxx.store_example.application.dto.LikedProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikedProductMapper {
    LikedProductDTO toDTO(LikedProduct likedProduct);
    LikedProduct toEntity(LikedProductDTO likedProductDTO);
}
