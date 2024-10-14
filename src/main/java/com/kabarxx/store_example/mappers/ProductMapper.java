package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.models.Product;
import com.kabarxx.store_example.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
    List<ProductDTO> toListDto(List<Product> products);
}
