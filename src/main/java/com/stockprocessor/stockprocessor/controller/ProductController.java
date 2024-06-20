package com.stockprocessor.stockprocessor.controller;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.dto.ProductDTO;
import com.stockprocessor.stockprocessor.mapper.ProductMapper;
import com.stockprocessor.stockprocessor.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController("api/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductEntity> productEntities = productService.getAllProducts();

        List<ProductDTO> productDTOs = productEntities.stream()
                .map(prod -> productMapper.toProductDTO(prod))
                .toList();

        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{productID}")
    public ResponseEntity<?> getProduct(@PathVariable long productID) {
        Optional<ProductEntity> productEntity = productService.getProduct(productID);

        return productEntity.isPresent() ? ResponseEntity.ok(productMapper.toProductDTO(productEntity.get()))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        ProductEntity productEntity = productService.createProduct(productMapper.toProductEntity(productDTO));

        return ResponseEntity.created(URI.create("/"+productEntity.getId())).build();
    }
}