package com.stockprocessor.stockprocessor.db.repository;

import com.stockprocessor.stockprocessor.db.ProductQtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQtyRepository extends JpaRepository<ProductQtyEntity,Long> {
}
