package com.stockprocessor.stockprocessor.service;

import com.stockprocessor.stockprocessor.dto.ProductDTO;


public interface ProductDeletedPublisher {

    void publish(ProductDTO productDTO);
}
