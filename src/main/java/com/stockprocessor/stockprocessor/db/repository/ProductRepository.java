package com.stockprocessor.stockprocessor.db.repository;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
}
