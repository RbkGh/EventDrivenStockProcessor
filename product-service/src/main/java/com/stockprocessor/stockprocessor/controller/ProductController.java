package com.stockprocessor.stockprocessor.controller;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.dto.ProductDTO;
import com.stockprocessor.stockprocessor.dto.ProductUpdatedPublisherDTO;
import com.stockprocessor.stockprocessor.exceptions.NotFoundHttpException;
import com.stockprocessor.stockprocessor.mapper.ProductMapper;
import com.stockprocessor.stockprocessor.service.ProductCreatedPublisherService;
import com.stockprocessor.stockprocessor.service.ProductDeletedPublisher;
import com.stockprocessor.stockprocessor.service.ProductService;
import com.stockprocessor.stockprocessor.service.ProductUpdatedPublisher;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private ProductService productService;
    private ProductMapper productMapper;
    private ProductCreatedPublisherService productPublisherService;
    private ProductUpdatedPublisher productUpdatedPublisher;
    private ProductDeletedPublisher productDeletedPublisher;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductEntity> productEntities = productService.getAllProducts();

        List<ProductDTO> productDTOs = productEntities.stream()
                .map(prod -> productMapper.toProductDTO(prod))
                .toList();

        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("{productID}")
    public ResponseEntity<?> getProduct(@PathVariable long productID) {
        Optional<ProductEntity> productEntity = productService.getProduct(productID);

        return productEntity.isPresent() ? ResponseEntity.ok(productMapper.toProductDTO(productEntity.get()))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        if (productService.doesProductShortCodeExist(productDTO.getProductShortCode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("productShortCode exists");
        } else {
            ProductEntity productEntity = productService.createProduct(productMapper.toProductEntity(productDTO));

            //publish to broker,run in thread to prevent any blocking of http response
            new Thread(() -> productPublisherService.publish(productMapper.toProductDTO(productEntity))).start();

            return ResponseEntity.created(URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + "/" + productEntity.getId())).build();
        }
    }

    @PutMapping("{productID}")
    public ResponseEntity<?> updateProduct(@PathVariable String productID, @RequestBody ProductDTO productDTO) throws NotFoundHttpException {
        try {
            ProductDTO productUpdated = productMapper.toProductDTO(productService.updateProduct(Long.valueOf(productID), productMapper.toProductEntity(productDTO)));


            //publish to update broker,run in seperate thread to prevent any blocking of http response
            new Thread(() -> {
                ProductUpdatedPublisherDTO productUpdatedPublisherDTO = ProductUpdatedPublisherDTO.builder().productDTOExisting(productDTO)
                        .getProductDTOUpdated(productUpdated).build();
                productUpdatedPublisher.publish(productUpdatedPublisherDTO);
            }).start();

        } catch (EntityNotFoundException exp) {
            throw new NotFoundHttpException();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productID) throws NotFoundHttpException {
        try {
            ProductDTO productDTO = productMapper.toProductDTO(productService.deleteProduct(productID));

            //publish to delete topic,run in seperate thread to prevent any blocking of http response
            new Thread(() -> {
                productDeletedPublisher.publish(productDTO);
            }).start();

        } catch (EntityNotFoundException exp) {
            throw new NotFoundHttpException();
        }

        return ResponseEntity.noContent().build();
    }
}
