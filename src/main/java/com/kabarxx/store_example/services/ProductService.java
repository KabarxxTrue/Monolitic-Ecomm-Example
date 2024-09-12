package com.kabarxx.store_example.services;

import com.kabarxx.store_example.domain.Product;
import com.kabarxx.store_example.domain.dto.ProductDTO;
import com.kabarxx.store_example.exceptions.product.ProductNotFoundException;
import com.kabarxx.store_example.mappers.ProductMapper;
import com.kabarxx.store_example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productMapper.toEntity(productDTO);
            product.setId(id);
            Product updatedProduct = productRepository.save(product);
            return productMapper.toDTO(updatedProduct);
        } else {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id))
            productRepository.deleteById(id);
        else
            throw new ProductNotFoundException("Product with id " + id + " not found");
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
         List<Product> products = productRepository.findAll();
         return productMapper.toListDto(products);
    }
}
