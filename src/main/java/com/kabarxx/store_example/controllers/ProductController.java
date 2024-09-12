package com.kabarxx.store_example.controllers;

import com.kabarxx.store_example.domain.dto.ProductDTO;
import com.kabarxx.store_example.exceptions.product.ProductDoesNotAddedException;
import com.kabarxx.store_example.exceptions.product.ProductNotFoundException;
import com.kabarxx.store_example.exceptions.product.ProductWasNotDeletedException;
import com.kabarxx.store_example.exceptions.product.ProductWasNotUpdatedException;
import com.kabarxx.store_example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        try {
            ProductDTO productDTO = productService.addProduct(product);
            return ResponseEntity.ok(productDTO);
        } catch (ProductDoesNotAddedException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestParam Long id, @RequestBody ProductDTO product) {
        try {
            ProductDTO productDTO = productService.updateProduct(id, product);
            return ResponseEntity.ok(productDTO);
        } catch (ProductWasNotUpdatedException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (ProductWasNotDeletedException e) {
            return ResponseEntity.notFound().build();
        }
    }
}