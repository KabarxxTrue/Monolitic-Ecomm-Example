package com.kabarxx.store_example.application.services;

import com.kabarxx.store_example.application.dto.ProductDTO;
import com.kabarxx.store_example.application.exceptions.product.ProductNotFoundException;
import com.kabarxx.store_example.domain.services.ProductDomainService;
import com.kabarxx.store_example.infrastructure.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDomainService productDomainService) {
        this.productRepository = productRepository;
        this.productDomainService = productDomainService;
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        var product = productDomainService.toEntity(productDTO);
        var savedProduct = productRepository.save(product);
        return productDomainService.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        var productOptional = productRepository.findById(id);

        productDomainService.checkIfProductExists(productOptional, id);

        var product = productDomainService.toEntity(productDTO);
        product.setId(id);

        var updatedProduct = productRepository.save(product);

        return productDomainService.toDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id))
            productRepository.deleteById(id);
        else
            throw new ProductNotFoundException("Product with id " + id + " not found");
    }

    public ProductDTO getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        return productDomainService.toDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        var products = productRepository.findAll();

        return productDomainService.toListDTO(products);
    }
}
