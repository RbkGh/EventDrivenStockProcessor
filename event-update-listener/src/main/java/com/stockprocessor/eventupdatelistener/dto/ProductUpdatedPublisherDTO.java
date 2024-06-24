package com.stockprocessor.eventupdatelistener.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdatedPublisherDTO {
    private ProductDTO productDTOExisting;

    private ProductDTO getProductDTOUpdated;
}
