package com.stockprocessor.stockprocessor.service;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.db.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProduct(Long productID) {
        return productRepository.findById(productID);
    }

    public ProductEntity createProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(Long productID,ProductEntity productEntity) throws EntityNotFoundException {
        if (!productRepository.existsById(productID)) {
            throw new EntityNotFoundException("product not found");
        }
        productEntity.setId(productID);
        return productRepository.save(productEntity);
    }

    public void deleteProduct(long productID) throws EntityNotFoundException {
        if (!productRepository.existsById(productID)) {
            throw new EntityNotFoundException("product not found");
        } else {
            productRepository.deleteById(productID);
        }
    }


}
