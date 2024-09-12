package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.domain.Product;
import com.kabarxx.store_example.domain.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
    List<ProductDTO> toDTO(List<Product> products);
}
