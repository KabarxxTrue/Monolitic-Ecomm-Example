package com.kabarxx.store_example.domain.services;

import com.kabarxx.store_example.application.dto.ProductDTO;
import com.kabarxx.store_example.application.exceptions.product.ProductNotFoundException;
import com.kabarxx.store_example.domain.models.Product;
import com.kabarxx.store_example.infrastructure.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDomainService {

    private final ProductMapper productMapper;

    @Autowired
    public ProductDomainService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public Product toEntity(ProductDTO productDTO) {
        return productMapper.toEntity(productDTO);
    }

    public ProductDTO toDTO(Product product) {
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> toListDTO(List<Product> products) {
        return productMapper.toListDto(products);
    }

    public void checkIfProductExists(Optional<Product> productOptional, Long id) {
        if (productOptional.isEmpty())
            throw new ProductNotFoundException("Product with id " + id + " not found");
    }
}
