package com.stockprocessor.stockprocessor.db.repository;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByIdAndDeleted(long productID, Integer deleted);
    Optional<ProductEntity> findByProductShortCode(String productShortCode);
}
