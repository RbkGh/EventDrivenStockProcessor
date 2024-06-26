package com.stockprocessor.eventupdatelistener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdatedPublisherDTO {
    private ProductDTO productDTOExisting;

    private ProductDTO getProductDTOUpdated;
}
