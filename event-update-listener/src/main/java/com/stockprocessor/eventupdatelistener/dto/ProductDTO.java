package com.stockprocessor.eventupdatelistener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String productName;
    private String productShortCode;
    private String productImageURL;
    private int productQty;
}
