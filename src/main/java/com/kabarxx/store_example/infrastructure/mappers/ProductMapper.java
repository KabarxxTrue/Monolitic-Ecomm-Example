package com.kabarxx.store_example.infrastructure.mappers;

import com.kabarxx.store_example.domain.models.Product;
import com.kabarxx.store_example.application.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
    List<ProductDTO> toListDto(List<Product> products);
}
