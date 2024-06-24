package com.stockprocessor.eventupdatelistener.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private String id;
    private String productName;
    private String productShortCode;
    private String productImageURL;
    private int productQty;
}
