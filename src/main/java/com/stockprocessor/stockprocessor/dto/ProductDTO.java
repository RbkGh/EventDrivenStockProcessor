package com.stockprocessor.stockprocessor.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String productName;
    private String productShortCode;
    private String productImageURL;
    private int productQty;
}
