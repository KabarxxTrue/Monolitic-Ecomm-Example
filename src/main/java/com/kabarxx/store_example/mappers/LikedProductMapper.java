package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.domain.LikedProduct;
import com.kabarxx.store_example.domain.dto.LikedProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikedProductMapper {
    LikedProductDTO toDTO(LikedProduct likedProduct);
    LikedProduct toEntity(LikedProductDTO likedProductDTO);
}
