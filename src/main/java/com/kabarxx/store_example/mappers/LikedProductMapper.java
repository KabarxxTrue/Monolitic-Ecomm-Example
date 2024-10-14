package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.models.LikedProduct;
import com.kabarxx.store_example.dto.LikedProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikedProductMapper {
    LikedProductDTO toDTO(LikedProduct likedProduct);
    LikedProduct toEntity(LikedProductDTO likedProductDTO);
}
