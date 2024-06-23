package com.stockprocessor.stockprocessor.service;

import com.stockprocessor.stockprocessor.dto.ProductUpdatedPublisherDTO;

public interface ProductUpdatedPublisher {
    void publish(ProductUpdatedPublisherDTO productUpdatedPublisherDTO);
}
