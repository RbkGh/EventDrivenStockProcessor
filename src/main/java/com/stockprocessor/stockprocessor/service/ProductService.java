package com.stockprocessor.stockprocessor.service;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.db.repository.ProductQtyRepository;
import com.stockprocessor.stockprocessor.db.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductQtyRepository productQtyRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProduct(Long productID) {
        return productRepository.findByIdAndDeleted(productID, 0);
    }

    public ProductEntity createProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(Long productID, ProductEntity productEntity) throws EntityNotFoundException {
        if (!productRepository.existsById(productID))
            throw new EntityNotFoundException("product not found");

        ProductEntity productEntityInDB = productRepository.findById(productID).get();

        //only allow product name and image changes since productshortcode is unique
        productEntityInDB.setProductName(productEntity.getProductName());
        productEntityInDB.setProductImageURL(productEntity.getProductImageURL());

        return productRepository.save(productEntityInDB);
    }

    public void deleteProduct(long productID) throws EntityNotFoundException {
        if (!productRepository.existsById(productID))
            throw new EntityNotFoundException("product not found");

        ProductEntity productEntity = productRepository.findById(productID).get();
        productEntity.setDeleted();

        productRepository.save(productEntity);
    }

    public boolean doesProductShortCodeExist(String productShortCode) {
        return productRepository.findByProductShortCode(productShortCode).isPresent();
    }

}
