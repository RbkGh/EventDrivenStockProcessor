package com.stockprocessor.stockprocessor.service;

import com.stockprocessor.stockprocessor.dto.ProductDTO;

public interface ProductCreatedPublisher {
    void publish(ProductDTO productDTO);
}
